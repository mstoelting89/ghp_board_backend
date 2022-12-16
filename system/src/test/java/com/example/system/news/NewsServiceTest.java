package com.example.system.news;

import com.example.system.attachment.AttachmentService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@WebAppConfiguration
@SpringBootTest
public class NewsServiceTest {

    private static final String NEWS_AUTHOR = "Michael";
    private static final LocalDateTime NEWS_DATE = LocalDateTime.parse("2022-06-01T21:16:20");
    private static final String NEWS_IMAGE = "src/test/resources/upload/test_1.jpg";
    private static final String NEWS_TEXT = "Dies ist der Beispieltext";
    private static final String NEWS_TITLE = "Testtitel";

    private static final String NEWS_AUTHOR_2 = "Svenja";
    private static final LocalDateTime NEWS_DATE_2 = LocalDateTime.parse("2022-05-01T21:16:20");
    private static final String NEWS_IMAGE_2 = "src/test/resources/upload/images/test_2.png";
    private static final String NEWS_TEXT_2 = "Dies ist der zweite Beispieltext";
    private static final String NEWS_TITLE_2 = "Testtitel Nr. 2";

    @Autowired
    private NewsService newsService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void shouldInsertNewNewsEntryWithImage() throws IOException {

        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        newsEntryDto.setNewsAuthor(NEWS_AUTHOR);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> image = Optional.of(multipartFile);

        var result = newsService.insertNewNewsEntry(newsEntryDto, image);

        assertNotNull(result);
        assertEquals(result.getNewsDate(), NEWS_DATE);
        assertEquals(result.getNewsText(), NEWS_TEXT);
        assertEquals(result.getNewsTitle(), NEWS_TITLE);
        assertEquals(result.getNewsAuthor(), NEWS_AUTHOR);
        assertNotNull(result.getNewsImage().getLocation());

        Files.delete(Paths.get(result.getNewsImage().getLocation()));
    }


    @Test
    public void shouldInsertNewNewsEntryWithoutImage() throws IOException {
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        newsEntryDto.setNewsAuthor(NEWS_AUTHOR);
        Optional<MultipartFile> image = Optional.empty();

        var result = newsService.insertNewNewsEntry(newsEntryDto, image);
        assertNotNull(result);
        assertEquals(result.getNewsDate(), NEWS_DATE);
        assertEquals(result.getNewsText(), NEWS_TEXT);
        assertEquals(result.getNewsTitle(), NEWS_TITLE);
        assertEquals(result.getNewsAuthor(), NEWS_AUTHOR);
        assertNull(result.getNewsImage());
    }
    @Test
    public void shouldReturnFailureIfDataIsMissingWhenInsertingData() throws IOException {
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        Optional<MultipartFile> image = Optional.empty();

        Throwable exception = assertThrows(NotFoundException.class, () -> {
            var result = newsService.insertNewNewsEntry(newsEntryDto, image);
        });

        assertEquals("Speichern fehlgeschlagen - Eintrag nicht vollständig", exception.getMessage());
    }

    @Test
    public void shouldUpdateExistingNewsEntryWithDifferentData() throws IOException {
        // arrange
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        newsEntryDto.setNewsAuthor(NEWS_AUTHOR);
        Optional<MultipartFile> image = Optional.empty();

        NewsEntryDto newsUpdatedEntryDto = new NewsEntryDto();
        newsUpdatedEntryDto.setNewsDate(NEWS_DATE_2);
        newsUpdatedEntryDto.setNewsText(NEWS_TEXT_2);
        newsUpdatedEntryDto.setNewsTitle(NEWS_TITLE);
        newsUpdatedEntryDto.setNewsAuthor(NEWS_AUTHOR);

        // act
        var result = newsService.insertNewNewsEntry(newsEntryDto, image);

        var updatedResult = newsService.updateNewsEntry(result.getId(), newsUpdatedEntryDto, image, false);

        // assert
        assertNotNull(updatedResult);
        assertEquals(updatedResult.getNewsDate(), NEWS_DATE_2);
        assertEquals(updatedResult.getNewsText(), NEWS_TEXT_2);
        assertEquals(updatedResult.getNewsTitle(), NEWS_TITLE);
        assertEquals(updatedResult.getNewsAuthor(), NEWS_AUTHOR);
        assertNull(updatedResult.getNewsImage());
    }

    @Test
    public void shouldUpdateExistingNewsEntryWithNewImage() throws IOException {
        // arrange
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        newsEntryDto.setNewsAuthor(NEWS_AUTHOR);
        Optional<MultipartFile> image = Optional.empty();

        var result = newsService.insertNewNewsEntry(newsEntryDto, image);

        // act
        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> newImage = Optional.of(multipartFile);
        var newResult = newsService.updateNewsEntry(result.getId(), newsEntryDto, newImage, false);

        // assert
        assertNull(result.getNewsImage());
        assertNotNull(newResult.getNewsImage());

        Files.delete(Paths.get(newResult.getNewsImage().getLocation()));
    }

    @Test
    public void shouldRemoveImageFromExistingNewsEntry() throws IOException {
        // arrange
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        newsEntryDto.setNewsAuthor(NEWS_AUTHOR);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> image = Optional.of(multipartFile);

        var result = newsService.insertNewNewsEntry(newsEntryDto, image);

        // act
        var newResult = newsService.updateNewsEntry(result.getId(), newsEntryDto, Optional.empty(), true);

        // assert
        assertNotNull(result.getNewsImage());
        assertNull(newResult.getNewsImage());
    }

    @Test
    public void shouldDeleteNewsEntryWithImage() throws IOException {
        // arrange
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        newsEntryDto.setNewsAuthor(NEWS_AUTHOR);

        File file = new File("src/test/resources/upload/test_1.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test_1.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input));

        Optional<MultipartFile> image = Optional.of(multipartFile);

        var result = newsService.insertNewNewsEntry(newsEntryDto, image);

        // act
        newsService.deleteNewsEntry(result.getId());
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            var newResult = newsService.getNewsEntry(result.getId());
        });

        // assert
        assertEquals(result.getNewsDate(), NEWS_DATE);
        assertEquals(result.getNewsText(), NEWS_TEXT);
        assertEquals(result.getNewsTitle(), NEWS_TITLE);
        assertEquals(result.getNewsAuthor(), NEWS_AUTHOR);
        assertNotNull(result.getNewsImage());

        assertEquals("Newsentry not found", exception.getMessage());

    }

    @Test
    public void shouldDeleteNewsEntryWithoutImage() throws IOException {
        // arrange
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        newsEntryDto.setNewsAuthor(NEWS_AUTHOR);

        var result = newsService.insertNewNewsEntry(newsEntryDto, Optional.empty());

        // act
        newsService.deleteNewsEntry(result.getId());
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            var newResult = newsService.getNewsEntry(result.getId());
        });

        // assert
        assertEquals(result.getNewsDate(), NEWS_DATE);
        assertEquals(result.getNewsText(), NEWS_TEXT);
        assertEquals(result.getNewsTitle(), NEWS_TITLE);
        assertEquals(result.getNewsAuthor(), NEWS_AUTHOR);
        assertNull(result.getNewsImage());

        assertEquals("Newsentry not found", exception.getMessage());
    }
}

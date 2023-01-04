package com.example.system.news;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
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
import java.nio.file.Path;
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
    private static final String NEWS_TEXT = "Dies ist der Beispieltext";
    private static final String NEWS_TITLE = "Testtitel";
    private static final String NEWS_AUTHOR_2 = "Peter";
    private static final LocalDateTime NEWS_DATE_2 = LocalDateTime.parse("2022-05-01T21:16:20");
    private static final String NEWS_TEXT_2 = "Dies ist der zweite Beispieltext";
    private static final String NEWS_TITLE_2 = "Testtitel2";

    @Autowired
    private NewsService newsService;

    @AfterEach
    public void clearTestDatabaseFromEntries() {
        var result = newsService.getAllNewsEntries();
        result.forEach(entry -> {
            try {
                newsService.deleteNewsEntry(entry.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void shouldGetOneSpecificNewsEntryWithoutImage() throws IOException {
        // arrange
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        newsEntryDto.setNewsAuthor(NEWS_AUTHOR);
        Optional<MultipartFile> image = Optional.empty();

        // act
        var result = newsService.insertNewNewsEntry(newsEntryDto, image);
        var newsEntry = newsService.getNewsEntry(result.getId());

        // assert
        assertNotNull(newsEntry);
        assertEquals(newsEntry.getNewsDate(), NEWS_DATE);
        assertEquals(newsEntry.getNewsText(), NEWS_TEXT);
        assertEquals(newsEntry.getNewsTitle(), NEWS_TITLE);
        assertEquals(newsEntry.getNewsAuthor(), NEWS_AUTHOR);
        assertEquals(newsEntry.getNewsImage(), "");
    }

    @Test
    public void shouldGetOneSpecificNewsEntryWithImage() throws IOException {
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

        // act
        var result = newsService.insertNewNewsEntry(newsEntryDto, image);
        var newsEntry = newsService.getNewsEntry(result.getId());

        // assert
        assertNotNull(newsEntry);
        assertEquals(newsEntry.getNewsDate(), NEWS_DATE);
        assertEquals(newsEntry.getNewsText(), NEWS_TEXT);
        assertEquals(newsEntry.getNewsTitle(), NEWS_TITLE);
        assertEquals(newsEntry.getNewsAuthor(), NEWS_AUTHOR);
        assertNotNull(newsEntry.getNewsImage());

        //Files.delete(Paths.get(result.getNewsImage().getLocation()));
    }

    @Test
    public void shouldGetAllNewsEnties() throws IOException {
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

        NewsEntryDto newsEntryDto2 = new NewsEntryDto();
        newsEntryDto2.setNewsDate(NEWS_DATE_2);
        newsEntryDto2.setNewsText(NEWS_TEXT_2);
        newsEntryDto2.setNewsTitle(NEWS_TITLE_2);
        newsEntryDto2.setNewsAuthor(NEWS_AUTHOR_2);

        File file2 = new File("src/test/resources/upload/test_3.jpg");
        FileInputStream input2 = new FileInputStream(file2);
        MultipartFile multipartFile2 = new MockMultipartFile("test_3.jpg",
                file.getName(), "image/jpg", IOUtils.toByteArray(input2));

        Optional<MultipartFile> image2 = Optional.of(multipartFile2);

        // act
        var result = newsService.insertNewNewsEntry(newsEntryDto, image);
        var result2 = newsService.insertNewNewsEntry(newsEntryDto2, image2);
        var newsEntries = newsService.getAllNewsEntries();

        // assert
        assertNotNull(newsEntries.get(0));
        assertEquals(newsEntries.get(0).getNewsDate(), NEWS_DATE);
        assertEquals(newsEntries.get(0).getNewsText(), NEWS_TEXT);
        assertEquals(newsEntries.get(0).getNewsTitle(), NEWS_TITLE);
        assertEquals(newsEntries.get(0).getNewsAuthor(), NEWS_AUTHOR);
        assertNotNull(newsEntries.get(0).getNewsImage());
        assertNotNull(newsEntries.get(1));
        assertEquals(newsEntries.get(1).getNewsDate(), NEWS_DATE_2);
        assertEquals(newsEntries.get(1).getNewsText(), NEWS_TEXT_2);
        assertEquals(newsEntries.get(1).getNewsTitle(), NEWS_TITLE_2);
        assertEquals(newsEntries.get(1).getNewsAuthor(), NEWS_AUTHOR_2);
        assertNotNull(newsEntries.get(1).getNewsImage());


        //Files.delete(Paths.get(result.getNewsImage().getLocation()));
        //Files.delete(Paths.get(result2.getNewsImage().getLocation()));
    }

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

        //Files.delete(Paths.get(result.getNewsImage().getLocation()));
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
    public void shouldReturnFailureIfDataIsMissingWhenInsertingData() {
        NewsEntryDto newsEntryDto = new NewsEntryDto();
        newsEntryDto.setNewsDate(NEWS_DATE);
        newsEntryDto.setNewsText(NEWS_TEXT);
        newsEntryDto.setNewsTitle(NEWS_TITLE);
        Optional<MultipartFile> image = Optional.empty();

        Throwable exception = assertThrows(NotFoundException.class, () -> newsService.insertNewNewsEntry(newsEntryDto, image));

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

        //Files.delete(Paths.get(newResult.getNewsImage().getLocation()));
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
        Throwable exception = assertThrows(NotFoundException.class, () -> newsService.getNewsEntry(result.getId()));

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
        Throwable exception = assertThrows(NotFoundException.class, () -> newsService.getNewsEntry(result.getId()));

        // assert
        assertEquals(result.getNewsDate(), NEWS_DATE);
        assertEquals(result.getNewsText(), NEWS_TEXT);
        assertEquals(result.getNewsTitle(), NEWS_TITLE);
        assertEquals(result.getNewsAuthor(), NEWS_AUTHOR);
        assertNull(result.getNewsImage());

        assertEquals("Newsentry not found", exception.getMessage());
    }
}

package com.example.system.news;

import com.example.system.attachment.AttachmentService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    @BeforeEach
    public void setup() {
        News news2 = new News();
        news2.setId(2L);
        news2.setNewsAuthor(NEWS_AUTHOR_2);
        news2.setNewsDate(NEWS_DATE_2);
        news2.setNewsText(NEWS_TEXT_2);
        news2.setNewsTitle(NEWS_TITLE_2);

        newsRepository.save(news2);
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

    // TODO: shouldUpdateExistingNewsEntryWithDifferentData
    @Test
    public void shouldUpdateExistingNewsEntryWithDifferentData() {
        // Neues News DTO erstellen mit unterschiedlichen Daten aus 1 und 2
        // vergleich ob korrekt

    }

    // TODO: shouldUpdateExistingNewsEntryWithNewImage
    // TODO: shouldRemoveImageFromExistingNewsEntry
    // TODO: shouldDeleteNewsEntryWithImage
    // TODO: shouldDeleteNewsEntryWithoutImage
}

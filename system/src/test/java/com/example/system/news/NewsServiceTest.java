package com.example.system.news;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@ActiveProfiles("test")
@WebAppConfiguration
@SpringBootTest
public class NewsServiceTest {
    // TODO: Test insert
    // TODO: Test update
    // TODO: Test delete

    private static final String NEWS_AUTHOR = "Michael";
    private static final LocalDateTime NEWS_DATE = LocalDateTime.parse("2022-06-01T21:16:20");
    private static final String NEWS_IMAGE = "src/test/resources/upload/test_1.jpg";
    private static final String NEWS_TEXT = "Dies ist der Beispieltext";
    private static final String NEWS_TITLE = "Testtitel";

    @Autowired
    private NewsService newsService;

    @Test
    public void shouldInsertNewNewsEntryWithImage() throws IOException {
        /*
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

        newsService.insertNewNewsEntry(newsEntryDto, image);
        */

    }
}

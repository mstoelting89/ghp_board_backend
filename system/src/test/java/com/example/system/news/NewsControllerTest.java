package com.example.system.news;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebAppConfiguration
@SpringBootTest
public class NewsControllerTest {
    /*
    // Testfälle:
    // erhalt von allen Newseinträgen
    // erhalt von einem Newseintrag


    // Noch zu implementieren:
    // Updaten von einem Newseintrag
    // Erstellen eines Newseintrages
    // Löschen eines Newseintrages

    private static final String NEWS_AUTHOR = "Michael";
    private static final LocalDateTime NEWS_DATE = LocalDateTime.parse("2022-06-01T21:16:20");
    private static final String NEWS_IMAGE = "location/test.png";
    private static final String NEWS_TEXT = "Dies ist der Beispieltext";
    private static final String NEWS_TITLE = "Testtitel";

    private static final String NEWS_AUTHOR_2 = "Svenja";
    private static final LocalDateTime NEWS_DATE_2 = LocalDateTime.parse("2022-05-01T21:16:20");
    private static final String NEWS_IMAGE_2 = "location/test_2.png";
    private static final String NEWS_TEXT_2 = "Dies ist der zweite Beispieltext";
    private static final String NEWS_TITLE_2 = "Testtitel Nr. 2";

    private static final String NEWS_AUTHOR_3 = "Ella";
    private static final LocalDateTime NEWS_DATE_3 = LocalDateTime.parse("2022-04-01T21:16:20");
    private static final String NEWS_IMAGE_3 = "location/test_3.png";
    private static final String NEWS_TEXT_3 = "Dies ist der dritte Beispieltext";
    private static final String NEWS_TITLE_3 = "Testtitel Nr. 3";

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private News news = new News();
    private News news2 = new News();

    @BeforeEach
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        news.setId(1L);
        news.setNewsAuthor(NEWS_AUTHOR);
        news.setNewsDate(NEWS_DATE);
        news.setNewsImage(NEWS_IMAGE);
        news.setNewsText(NEWS_TEXT);
        news.setNewsTitle(NEWS_TITLE);

        newsRepository.save(news);

        news2.setId(2L);
        news2.setNewsAuthor(NEWS_AUTHOR_2);
        news2.setNewsDate(NEWS_DATE_2);
        news2.setNewsImage(NEWS_IMAGE_2);
        news2.setNewsText(NEWS_TEXT_2);
        news2.setNewsTitle(NEWS_TITLE_2);

        newsRepository.save(news2);
    }

    @Test
    public void shouldGetAllNews() throws Exception {

        final var mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/news")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<News> newsEntries = objectMapper.readValue(response, new TypeReference<List<News>>() {});

        assertFalse(newsEntries.isEmpty());

        var news = newsEntries.get(0);

        assertEquals(news.getId(), 1L);
        assertEquals(news.getNewsAuthor(), NEWS_AUTHOR);
        assertEquals(news.getNewsText(), NEWS_TEXT);
        assertEquals(news.getNewsTitle(), NEWS_TITLE);
        assertEquals(news.getNewsDate(), NEWS_DATE);
        assertEquals(news.getNewsImage(), NEWS_IMAGE);

        var news2 = newsEntries.get(1);

        assertEquals(news2.getId(), 2L);
        assertEquals(news2.getNewsAuthor(), NEWS_AUTHOR_2);
        assertEquals(news2.getNewsText(), NEWS_TEXT_2);
        assertEquals(news2.getNewsTitle(), NEWS_TITLE_2);
        assertEquals(news2.getNewsDate(), NEWS_DATE_2);
        assertEquals(news2.getNewsImage(), NEWS_IMAGE_2);
    }

    @Test
    public void shouldGetOneSpecificNewsEntry() throws Exception {

        final var mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/news/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        News newsEntry = objectMapper.readValue(response, News.class);

        assertEquals(newsEntry.getId(), 1L);
        assertEquals(newsEntry.getNewsAuthor(), NEWS_AUTHOR);
        assertEquals(newsEntry.getNewsText(), NEWS_TEXT);
        assertEquals(newsEntry.getNewsTitle(), NEWS_TITLE);
        assertEquals(newsEntry.getNewsDate(), NEWS_DATE);
        assertEquals(newsEntry.getNewsImage(), NEWS_IMAGE);
    }

    @Test
    public void shouldInsertNewsEntry() throws Exception {

        //JSONObject obj = new JSONObject();
        //obj.put("newsDate", NEWS_DATE_3);
        //obj.put("newsTitle", NEWS_TITLE_3);
        //obj.put("newsText", NEWS_TEXT_3);
        //obj.put("newsAuthor", NEWS_AUTHOR_3);
        //obj.put("newsImage", NEWS_IMAGE_3);
//
        //final var mvcResult = this.mockMvc.perform(
        //    MockMvcRequestBuilders.post("/api/v1/news/", obj.get)
        //            .accept(MediaType.APPLICATION_JSON))
        //    .andExpect(status().isOk())
        //    .andReturn();
//
        //System.out.println("Foo");

    }
    */
}

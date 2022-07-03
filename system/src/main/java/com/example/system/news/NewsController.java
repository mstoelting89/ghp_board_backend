package com.example.system.news;

import com.example.system.security.jwt.JwtAuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import javax.lang.model.element.NestingKind;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
public class NewsController {

    @Autowired
    private final NewsService newsService;
    private JwtAuthenticationService jwtAuthenticationService;

    @GetMapping(path = "/api/v1/news", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllNews(HttpServletRequest request) {
        return new ResponseEntity<>(newsService.getAllNewsEntries(), HttpStatus.OK);
    }

    @GetMapping(path = "/api/v1/news/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNews(@PathVariable("id") Long id, HttpServletRequest request) {

        return new ResponseEntity<>(newsService.getNewsEntry(id), HttpStatus.OK);
    }

    // Post -> receive a new NewsEntry
    @PostMapping(
            path = "/api/v1/news",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNews(@RequestParam("file") Optional<MultipartFile> file, @RequestParam("newsData") String newsEntryString, HttpServletRequest request) throws IOException {

        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        NewsEntryDto newsEntryDto = mapper.readValue(newsEntryString, NewsEntryDto.class);

        return new ResponseEntity<>(newsService.insertNewNewsEntry(newsEntryDto, file), HttpStatus.OK);
    }

    //Put -> update an existing NewsEntry
    @PutMapping(
            path = "/api/v1/news",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateNews(
            @RequestParam("file") Optional<MultipartFile> file,
            @RequestParam("newsUpdateId") Long newsUpdateId,
            @RequestParam("newsData") String newsUpdateString,
            HttpServletRequest request) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        NewsEntryDto newsUpdateDto = mapper.readValue(newsUpdateString, NewsEntryDto.class);

        return new ResponseEntity<>(newsService.updateNewsEntry(newsUpdateId, newsUpdateDto, file), HttpStatus.OK);
    }

    //Delete -> delete an existing NewsEntry
    @DeleteMapping(
            path = "/api/v1/news/{id}"
    )
    public ResponseEntity<?> deleteNews(@PathVariable("id") Long newsDeleteId) throws IOException {
        newsService.deleteNewsEntry(newsDeleteId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

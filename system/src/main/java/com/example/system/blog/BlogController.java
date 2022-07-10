package com.example.system.blog;

import com.example.system.demand.DemandEntryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin
public class BlogController {

    private BlogService blogService;

    @GetMapping(path = "/api/v1/blog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBlogPosts(HttpServletRequest request) {
        return new ResponseEntity<>(blogService.getAllBlogPosts(), HttpStatus.OK);
    }

    @PostMapping(
            path = "/api/v1/blog",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> insertNewBlog(
            @RequestParam Optional<List<MultipartFile>> files,
            @RequestParam String blogData
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        BlogEntryDto blogEntryDto = mapper.readValue(blogData, BlogEntryDto.class);

        return new ResponseEntity<>(blogService.insertNewBlogEntry(blogEntryDto, files), HttpStatus.OK);
    }

    @DeleteMapping(
            path = "/api/v1/blog/{id}"
    )
    public ResponseEntity<?> deleteBlog(@PathVariable("id") Long blogDeleteId) throws IOException {
        blogService.deleteBlogEntry(blogDeleteId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}

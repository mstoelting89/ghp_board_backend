package com.example.system.attachment;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@RestController
@AllArgsConstructor
@CrossOrigin
public class AttachmentController {

    private AttachmentService attachmentService;

    @GetMapping(path = "/api/v1/attachment/{id}")
    public ResponseEntity<String> getImage(@PathVariable("id") Long id) {
        return new ResponseEntity<String>(attachmentService.getAttachmentAsBase64(id), HttpStatus.OK);
    }
}

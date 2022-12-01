package com.example.system.contact;

import com.example.system.config.GhpProperties;
import com.example.system.email.EmailService;
import lombok.AllArgsConstructor;
import org.h2.jdbc.meta.DatabaseMetaServer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin
@EnableConfigurationProperties(GhpProperties.class)
public class ContactController {

    private EmailService emailService;
    private SpringTemplateEngine springTemplateEngine;
    private final GhpProperties ghpProperties;

    @PostMapping(path = "/api/v1/contact")
    public ResponseEntity<?> sendContactMail(@RequestBody ContactDto contactDto) {

        if (emailService.checkEmail(contactDto.getEmail())) {

            Map<String, Object> model = new HashMap<>();
            model.put("email", contactDto.getEmail());
            model.put("name", contactDto.getLastName());
            model.put("vorname", contactDto.getFirstName());
            model.put("text", contactDto.getMessage());
            Context context = new Context();
            context.setVariables(model);

            String html = springTemplateEngine.process("new-contact", context);

            emailService.send(ghpProperties.contactEmail, html, "Guitar Hearts Project: Neue Kontaktanfrage");
            return new ResponseEntity<>("Kontaktnachricht wurde erfolgreich verschickt", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bitte geben Sie eine korrekte Email-Adresse an", HttpStatus.OK);
        }

    }
}

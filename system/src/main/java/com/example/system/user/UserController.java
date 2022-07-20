package com.example.system.user;

import com.example.system.news.NewsEntryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@RestController
@CrossOrigin
@AllArgsConstructor

public class UserController {

    private UserService userService;

    @PostMapping(
            path = "/api/v1/user/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUserList(@RequestBody UserTokenDto userTokenDto) {
        return new ResponseEntity<>(userService.getUserList(userTokenDto), HttpStatus.OK);
    }

    @PostMapping(
            path =  "/api/v1/user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDto newUser) {
        return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.OK);
    }

    @GetMapping(
            path = "/api/v1/user/user_roles",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUserRoles() {
        return new ResponseEntity<>(UserRole.values(), HttpStatus.OK);
    }

    @PutMapping(
            path = "/api/v1/user/role",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> changeUserRole(@RequestBody UserRegistrationDto userChangeDto) {
        System.out.println(userChangeDto.getEmail());
        System.out.println(userChangeDto.getUserRole());
        return new ResponseEntity<>(userService.changeUserRole(userChangeDto), HttpStatus.OK);
    }

    @DeleteMapping(
            path = "/api/v1/user/{id}"
    )
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userDeleteId) {
        return new ResponseEntity<>(userService.deleteUser(userDeleteId), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handlerIllegalStateException(IllegalStateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}

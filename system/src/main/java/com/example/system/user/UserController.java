package com.example.system.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor

public class UserController {

    private UserService userService;

    @PostMapping(
            path =  "/api/v1/user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDto newUser) {
        System.out.println(newUser.getEmail());
        System.out.println(newUser.getUserRole());
        return new ResponseEntity<>(userService.createUser(newUser), HttpStatus.OK);
    }

}

package com.example.system.security.authentication;

import com.example.system.security.jwt.JwtTokenDto;
import com.example.system.user.User;
import com.example.system.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping(path = "/api/v1/login")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody AuthenticationDto authenticationDto) {

        JwtTokenDto token = authenticationService.generateJwtToken(authenticationDto.getEmail(), authenticationDto.getPassword());
        User user = userService.loadUserByMail(authenticationDto.getEmail());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(
                token.getToken(),
                user.getAuthorities()
        );

        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/forgotpassword")
    @ResponseBody
    public String forgotPassword () {
        return "foo";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

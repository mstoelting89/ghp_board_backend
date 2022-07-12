package com.example.system.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {

    private String token;
    private Collection<?> role;
    private String email;
}

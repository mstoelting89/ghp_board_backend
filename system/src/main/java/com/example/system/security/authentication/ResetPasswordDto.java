package com.example.system.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {
    private String password;
    private String confirmToken;
}

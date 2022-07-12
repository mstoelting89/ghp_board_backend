package com.example.system.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationDto {
    private String email;
    private Long userRole;
}

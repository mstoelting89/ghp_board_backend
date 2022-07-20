package com.example.system.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserTokenDto {
    private String email;
    private String userToken;
}

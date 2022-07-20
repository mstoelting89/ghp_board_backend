package com.example.system.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserListDto {
    private Long id;
    private String email;
    private UserRole userRole;
    private Boolean isEnabled;
}

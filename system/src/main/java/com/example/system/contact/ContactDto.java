package com.example.system.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactDto {
    private String firstName;
    private String lastName;
    private String email;
    private String message;
}

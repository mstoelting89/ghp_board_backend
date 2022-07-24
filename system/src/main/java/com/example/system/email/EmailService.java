package com.example.system.email;

public interface EmailService {
    void send(String to, String email, String subject);

    boolean checkEmail(String email);
}

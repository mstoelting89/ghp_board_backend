package com.example.system.token;

import com.example.system.user.User;

import java.time.LocalDateTime;

public interface TokenService {

    Token createToken(LocalDateTime createdAt, LocalDateTime expiresAt, User user);

    Token getToken(String token);

    Token updateConfirmedAt(String token, LocalDateTime confirmedAt);

    Boolean validateToken(String token);

    void deleteTokenByUser(User user);
}

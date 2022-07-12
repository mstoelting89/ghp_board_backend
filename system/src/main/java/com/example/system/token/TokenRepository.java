package com.example.system.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository   extends JpaRepository<Token, Long> {
    Token findTokenByToken(String token);
}

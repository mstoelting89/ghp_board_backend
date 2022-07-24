package com.example.system.token;

import com.example.system.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository   extends JpaRepository<Token, Long> {
    Token findTokenByToken(String token);
    void deleteAllByUser(User user);
}

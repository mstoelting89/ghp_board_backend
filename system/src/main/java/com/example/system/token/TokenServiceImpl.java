package com.example.system.token;

import com.example.system.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private TokenRepository tokenRepository;

    @Override
    public Token createToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        return tokenRepository.save(new Token(
                token,
                createdAt,
                expiresAt,
                user
        ));
    }

    @Override
    public Token getToken(String token) {
        return tokenRepository.findTokenByToken(token);
    }

    @Override
    public Token updateConfirmedAt(String token, LocalDateTime confirmedAt) {
        Token currentToken = tokenRepository.findTokenByToken(token);
        currentToken.setConfirmedAt(confirmedAt);

        return tokenRepository.save(currentToken);
    }

    @Override
    public Boolean validateToken(String token) {
        return null;
    }
}

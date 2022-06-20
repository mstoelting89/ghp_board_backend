package com.example.system.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenService {

    private Long expiration;
    private String secret;

    public JwtTokenService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") Long expiration
            ) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(String email) {
        final Date createDate = new Date();
        final Date expirationDate = calculateExpirationDate(createDate);

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(email)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date calculateExpirationDate(Date createDate) {
        return new Date(createDate.getTime() + expiration * 10000);
    }
}

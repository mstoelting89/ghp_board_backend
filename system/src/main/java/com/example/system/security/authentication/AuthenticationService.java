package com.example.system.security.authentication;

import com.example.system.security.jwt.JwtTokenDto;
import com.example.system.security.jwt.JwtTokenService;
import com.example.system.token.Token;
import com.example.system.token.TokenService;
import com.example.system.user.User;
import com.example.system.user.UserRepository;
import com.example.system.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private UserRepository userRepository;
    private JwtTokenService jwtTokenService;
    private PasswordEncoder passwordEncoder;

    public JwtTokenDto generateJwtToken(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> new JwtTokenDto(jwtTokenService.generateToken(email)))
                .orElseThrow(() -> new EntityNotFoundException("Email oder Passwort nicht korrekt."));
    }
}

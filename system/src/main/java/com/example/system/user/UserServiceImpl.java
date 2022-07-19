package com.example.system.user;

import com.example.system.email.EmailService;
import com.example.system.security.authentication.RequestPasswordDto;
import com.example.system.security.authentication.ResetPasswordDto;
import com.example.system.token.Token;
import com.example.system.token.TokenService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.linkbuilder.ILinkBuilder;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailService emailService;
    private TokenService tokenService;
    private SpringTemplateEngine springTemplateEngine;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Die Email existiert nicht."));
    }

    @Override
    public User loadUserByMail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Die Email existiert nicht."));
    }

    @Override
    public String createUser(UserRegistrationDto userRegistrationDto) {
        // check if user exists;
        boolean userExists = userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent();

        if(userExists) {
            throw new IllegalStateException("Die Email ist bereits hinterlegt");
        }

        UserRole userRole;

        if (userRegistrationDto.getUserRole() == 0) {
            userRole = UserRole.ADMIN;
        } else if(userRegistrationDto.getUserRole() == 1) {
            userRole = UserRole.REDAKTEUR;
        } else {
            userRole = UserRole.USER;
        }

        String password = RandomStringUtils.randomAlphanumeric(12);

        var savedUser = userRepository.save(new User(
                        userRegistrationDto.getEmail(),
                        bCryptPasswordEncoder.encode(password),
                        userRole,
                        false,
                        false)
                );

        var token = tokenService.createToken(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                savedUser
        );

        String link = "http://localhost:8081/login?confirmToken=" + token.getToken();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("email", savedUser.getEmail());
        model.put("link", link);
        Context context = new Context();
        context.setVariables(model);

        String html = springTemplateEngine.process("reset-password-template", context);

        //String email = "Hallo, du wurdest eingeladen am Guitarhearts Project teilzunehmen. Über diesen Link kannst du dich anmeldern: " + link;
        emailService.send("michaelstoelting@gmail.com", html, "Einladung zur Teilnahme am Guitar Hearts Project");

        return "User " + savedUser.getEmail() + " erfolgreich angelegt. Passwort Email wurde verschickt.";

    }

    @Override
    public String resetPassword(ResetPasswordDto resetPasswordDto) throws UsernameNotFoundException {
        // get user from token
        Token token = tokenService.getToken(resetPasswordDto.getConfirmToken());
        if (token != null) {
            User user = token.getUser();
            user.setPassword(bCryptPasswordEncoder.encode(resetPasswordDto.getPassword()));
            userRepository.save(user);
            return "Password erfolgreich gesetzt";
        } else {
            throw new UsernameNotFoundException("Die Email existiert nicht.");
        }

    }

    @Override
    public String requestPassword(RequestPasswordDto requestPasswordDto) {
        User user = userRepository.findByEmail(requestPasswordDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Die Email existiert nicht."));

        var token = tokenService.createToken(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                user
        );

        String link = "<a href='http://localhost:8081/login?confirmToken=" + token.getToken() + "'>Zur Anmeldung</a>";
        String email = "Hallo, du wurdest eingeladen am Guitarhearts Project teilzunehmen. Über diesen Link kannst du dich anmeldern: " + link;
        emailService.send("michaelstoelting@gmail.com", email, "Guitar Hearts Project: Passwort zurücksetzen");

        return "Eine Email zum Zurücksetzen des Passworts wurde verschickt";
    }
}

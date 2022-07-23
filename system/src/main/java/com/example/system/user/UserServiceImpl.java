package com.example.system.user;

import com.example.system.email.EmailService;
import com.example.system.security.authentication.RequestPasswordDto;
import com.example.system.security.authentication.ResetPasswordDto;
import com.example.system.security.jwt.JwtAuthenticationService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailService emailService;
    private TokenService tokenService;
    private SpringTemplateEngine springTemplateEngine;
    private JwtAuthenticationService jwtAuthenticationService;

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

        String password = RandomStringUtils.randomAlphanumeric(12);

        var savedUser = userRepository.save(new User(
                        userRegistrationDto.getEmail(),
                        bCryptPasswordEncoder.encode(password),
                        userRegistrationDto.getUserRole(),
                        false,
                        false)
                );

        var token = tokenService.createToken(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                savedUser
        );

        String link = "https://ghp.stoelting-michael.de/login?confirmToken=" + token.getToken();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("email", savedUser.getEmail());
        model.put("link", link);
        Context context = new Context();
        context.setVariables(model);

        String html = springTemplateEngine.process("new-member-template", context);

        // TODO: change email to user email
        emailService.send(savedUser.getEmail(), html, "Guitar Hearts Project: Einladung zum Board");

        return "User " + savedUser.getEmail() + " erfolgreich angelegt. Passwort Email wurde verschickt.";

    }

    @Override
    public String resetPassword(ResetPasswordDto resetPasswordDto) throws UsernameNotFoundException {
        // get user from token
        Token token = tokenService.getToken(resetPasswordDto.getConfirmToken());
        System.out.println(token.getConfirmedAt());
        if (token != null) {
            if (token.getConfirmedAt() != null) {
                throw new IllegalStateException("Das Passwort wurde bereits gesetzt");
            }
            User user = token.getUser();
            user.setPassword(bCryptPasswordEncoder.encode(resetPasswordDto.getPassword()));
            if (!user.getEnabled()) {
                user.setEnabled(true);
            }
            userRepository.save(user);
            tokenService.updateConfirmedAt(token.getToken(), LocalDateTime.now());
            return "Das Passwort wurde erfolgreich gesetzt";
        } else {
            throw new UsernameNotFoundException("Die Email existiert nicht.");
        }

    }

    @Override
    public String requestToResetPassword(RequestPasswordDto requestPasswordDto) {
        User user = userRepository.findByEmail(requestPasswordDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Die Email existiert nicht."));

        var token = tokenService.createToken(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                user
        );

        String link = "https://ghp.stoelting-michael.de/login?confirmToken=" + token.getToken();
        Map<String, Object> model = new HashMap<>();
        model.put("email", user.getEmail());
        model.put("link", link);
        Context context = new Context();
        context.setVariables(model);

        String html = springTemplateEngine.process("password-reset-template", context);

        // TODO: change email to user email
        emailService.send(user.getEmail(), html, "Guitar Hearts Project: Passwort zurücksetzen");

        return "Eine Email zum Zurücksetzen des Passworts wurde verschickt";
    }

    @Override
    public List<UserListDto> getUserList(UserTokenDto userTokenDto) {
        // check if user got the admin user role
        String userEmail = jwtAuthenticationService.getEmailFromToken(userTokenDto.getUserToken());
        User admin = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Der User existiert nicht."));

        if (!admin.getUserRole().equals(UserRole.ADMIN)) {
            throw new IllegalStateException("Sie haben nicht die erforderlichen Rechte");
        }

        List<User> userList = userRepository.findAll();
        List<UserListDto> resultList = new ArrayList<>();

        userList.forEach(user -> {
            resultList.add(new UserListDto(user.getId(), user.getEmail(), user.getUserRole(), user.getEnabled()));
        });
        return resultList;
    }

    @Override
    public String changeUserRole(UserRegistrationDto userChangeDto) {
        User user = userRepository.findByEmail(userChangeDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Der User existiert nicht."));

        user.setUserRole(userChangeDto.getUserRole());
        userRepository.save(user);
        return "Userlevel wurde erfolgreich geändert";
    }

    @Override
    public String deleteUser(Long userDeleteId) {
        User user = userRepository.findById(userDeleteId)
                .orElseThrow(() -> new UsernameNotFoundException("Der User existiert nicht."));

        userRepository.delete(user);
        return "Der User " + user.getEmail() + " wurde erfolgreich gelöscht";
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void sendToAll(String template, String subject) {
        List<User> userList = getAllUsers();

        userList.forEach(user -> {
            // TODO: change email to user email
            Map<String, Object> model = new HashMap<>();
            model.put("email", user.getEmail());
            Context context = new Context();
            context.setVariables(model);

            String html = springTemplateEngine.process(template, context);

            emailService.send(user.getEmail(), html, subject);
        });
    }
}

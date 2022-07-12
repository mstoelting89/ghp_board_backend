package com.example.system.user;

import com.example.system.email.EmailService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User loadUserByMail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User createUser(UserRegistrationDto userRegistrationDto) {
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

        User savedUser = userRepository.save(new User(
                        userRegistrationDto.getEmail(),
                        bCryptPasswordEncoder.encode(password),
                        userRole,
                        false,
                        false)
                );

        emailService.send("michaelstoelting@gmail.com", "Dies ist das initiale Password für " + savedUser.getEmail() + ": " + password);
        // TODO:
        // - [x] create UserDto
        // - [x] Hash Password
        // - [x] save User with initial password
        // - [x] set isEnabled to false
        // - [x] send email to User with initial password

        return savedUser;

    }
}

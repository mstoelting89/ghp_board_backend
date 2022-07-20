package com.example.system.user;

import com.example.system.security.authentication.RequestPasswordDto;
import com.example.system.security.authentication.ResetPasswordDto;

public interface UserService {

    User loadUserByMail(String email);

    String createUser(UserRegistrationDto userRegistrationDto);

    String resetPassword(ResetPasswordDto resetPasswordDto);

    String requestToResetPassword(RequestPasswordDto requestPasswordDto);
}

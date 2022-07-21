package com.example.system.user;

import com.example.system.security.authentication.RequestPasswordDto;
import com.example.system.security.authentication.ResetPasswordDto;

import java.util.List;

public interface UserService {

    User loadUserByMail(String email);

    String createUser(UserRegistrationDto userRegistrationDto);

    String resetPassword(ResetPasswordDto resetPasswordDto);

    String requestToResetPassword(RequestPasswordDto requestPasswordDto);

    List<UserListDto> getUserList(UserTokenDto userTokenDto);

    String changeUserRole(UserRegistrationDto userChangeDto);

    String deleteUser(Long userDeleteId);

    List<User> getAllUsers();

    void sendToAll(String template, String subject);
}

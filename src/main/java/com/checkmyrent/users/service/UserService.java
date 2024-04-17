package com.checkmyrent.users.service;

import com.checkmyrent.users.shared.dto.UserDTO;
import com.checkmyrent.users.ui.model.response.UserResponseModel;

import java.util.List;

public interface UserService  {
    UserDTO createUser(UserDTO user);
    UserDTO getUser(String email);
    List<UserResponseModel> getUsers();

    // UserDTO getUserByUserId(String id);
    UserDTO getUserByUserId(String userId);
    UserDTO updateUser(String userId, UserDTO user);
    void deleteUser(String userId);

    // boolean verifyEmailToken(String token);
    // boolean requestPasswordReset(String email);
    // boolean resetPassword(String token, String password);

}

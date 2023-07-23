package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;

public interface UserService {
    User getCurrentUser(String username);


    UserModel updateUser(UserModel authUser, User user);


    UserModel getUser(String username);


    User setUserPassword(UserModel authUser, NewPassword newPassword);


    User loadUserImage(UserModel authUser, ImageModel image);


    boolean isPasswordCorrect(UserModel authUser, String currentPassword);


    void createUser(Register registerReqDto, Role role);
}

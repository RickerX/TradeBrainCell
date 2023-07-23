package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;

public interface UserService {
    User getCurrentUser(String username);

    /**
     * Update current authorized user
     * @param authUser current authorized user
     * @param user new user data
     * @return updated user
     */
    UserModel updateUser(UserModel authUser, User user);

    /**
     * Get user entity by username
     * @param username username
     * @return user entity
     */
    UserModel getUser(String username);

    /**
     * Set password for current authorized user
     * @param authUser current authorized user
     * @param newPassword new password
     * @return updated user
     */
    User setUserPassword(UserModel authUser, NewPassword newPassword);

    /**
     * Load new user image (avatar)
     * @param authUser current authorized user
     * @param image new image
     * @return updated user
     */
    User loadUserImage(UserModel authUser, ImageModel image);

    /**
     * Password validation check
     * @param authUser current authorized user
     * @param currentPassword current user password
     * @return true if the password is correct
     */
    boolean isPasswordCorrect(UserModel authUser, String currentPassword);

    /**
     * Create new user
     * @param registerReqDto new user details
     * @param role role for new user
     */
    void createUser(Register registerReqDto, Role role);
}

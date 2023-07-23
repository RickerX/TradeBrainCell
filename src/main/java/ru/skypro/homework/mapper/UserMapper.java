package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;

import java.util.Optional;
@Component
public class UserMapper {
    /**
     * Mapping user model to user dto
     * @param userModel original model
     * @return resulting user dto
     */
    public User mapToUser(UserModel userModel) {
        User user = new User();
        user.setId(userModel.getId());
        user.setEmail(userModel.getUsername());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPhone(userModel.getPhone());
        user.setImage(Optional.ofNullable(userModel.getImage())
                .map(ImageModel::getPath)
                .orElse(null));
        return user;
    }

    /**
     * Mapping user  to user model
     * @param user original user
     * @param userModel user model template
     * @return resulting user model
     */
    public UserModel mapUserToUserModel(User user, UserModel userModel) {
        userModel.setId(user.getId());
        userModel.setUsername(user.getEmail());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setPhone(user.getPhone());
        return userModel;
    }

    /**
     * Mapping register to user model
     * @param register original user
     * @param userModel user model template
     * @return resulting user model
     */
    public UserModel mapRegisterReqToUserModel(Register register, UserModel userModel) {
        userModel.setUsername(register.getUsername());
        userModel.setPassword(register.getPassword());
        userModel.setFirstName(register.getFirstName());
        userModel.setLastName(register.getLastName());
        userModel.setPhone(register.getPhone());
        userModel.setRole(register.getRole());
        return userModel;
    }
}

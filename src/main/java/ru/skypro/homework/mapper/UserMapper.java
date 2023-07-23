package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;

import java.util.Optional;
@Component
public class UserMapper {
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

    public UserModel mapUserToUserModel(User user, UserModel userModel) {
        userModel.setId(user.getId());
        userModel.setUsername(user.getEmail());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setPhone(user.getPhone());
        return userModel;
    }

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

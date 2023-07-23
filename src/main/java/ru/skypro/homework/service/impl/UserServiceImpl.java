package ru.skypro.homework.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Objects;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getCurrentUser(String username) {
        return mapper.mapToUser(getUser(username));
    }

    @Override
    public UserModel getUser(String username) {
        return repository.findUserByUsername(username).orElseThrow();
    }

    @Override
    public UserModel updateUser(UserModel authUser, User user) {
        UserModel userModel = mapper.mapUserToUserModel(user, authUser);
        repository.save(userModel);
        return userModel;
    }

    @Override
    public User setUserPassword(UserModel authUser, NewPassword newPassword) {
        authUser.setPassword(passwordEncoder.encode(newPassword.newPassword));
        repository.save(authUser);
        return mapper.mapToUser(authUser);
    }

    @Override
    public User loadUserImage(UserModel userModel, ImageModel image) {
        image.setId(Optional.ofNullable(userModel.getImage())
                .map(ImageModel::getId)
                .orElse(null));
        userModel.setImage(image);
        //userModel.setContentType(image.getContentType());
        repository.save(userModel);
        return mapper.mapToUser(userModel);
    }

    @Override
    public boolean isPasswordCorrect(UserModel authUser, String currentPassword) {
        return passwordEncoder.matches(currentPassword, authUser.getPassword());
    }

    @Override
    public void createUser(Register register, Role role) {
        UserModel userModel = mapper.mapRegisterReqToUserModel(register, new UserModel());
        userModel.setPassword(passwordEncoder.encode(register.getPassword()));
        userModel.setRole(Objects.requireNonNullElse(role, Role.USER));
        repository.save(userModel);
    }
}

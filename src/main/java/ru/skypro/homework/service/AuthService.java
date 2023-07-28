package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register registerReq, Role role);

    boolean setPassword(NewPassword newPassword, String name);
}

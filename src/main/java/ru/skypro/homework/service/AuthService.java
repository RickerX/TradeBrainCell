package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;

public interface AuthService {
    /**
     * Authorization user
     * @param userName login for enter
     * @param password password for enter
     * @return authorization success
     */
    boolean login(String userName, String password);

    /**
     * adding a new user to the database
     * @param registerReqDto user data for new user
     * @param role role for new user
     * @return registration success
     */
    boolean register(Register registerReqDto, Role role);
}

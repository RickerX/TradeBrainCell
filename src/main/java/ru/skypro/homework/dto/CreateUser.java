package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateUser {
    private String password;
    private String phone;
    private String lastName;
    private String firstName;
    private String email;
}

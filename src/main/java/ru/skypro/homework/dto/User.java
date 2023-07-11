package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class User {
    private String phone;
    private String lastName;
    private String firstName;
    private String email;
    private Integer id;
}

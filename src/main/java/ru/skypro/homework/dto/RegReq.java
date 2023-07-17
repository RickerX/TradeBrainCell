package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class RegReq {
    private String password;
    private String username;
    private Role role;
}

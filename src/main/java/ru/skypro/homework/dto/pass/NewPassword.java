package ru.skypro.homework.dto.pass;

import lombok.Data;

@Data
public class NewPassword {
    private String newPassword;
    private String currentPassword;
}

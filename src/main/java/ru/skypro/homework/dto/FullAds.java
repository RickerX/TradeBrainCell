package ru.skypro.homework.dto;

import lombok.Data;
@Data
public class FullAds {
    private long pk;                // id объявления
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;           // логин автора объявления
    private String image;
    private String phone;
    private int price;
    private String title;
}

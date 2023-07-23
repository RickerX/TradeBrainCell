package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class FullAds {
    private long pk;                // id объявления
    private String authorFirstName; // имя автора объявления
    private String authorLastName;  // фамилия автора объявления
    private String description;     // описание объявления
    private String email;           // логин автора объявления
    private String image;           // ссылка на картинку объявления
    private String phone;           // телефон автора объявления
    private int price;              // цена объявления
    private String title;           // заголовок объявления
}

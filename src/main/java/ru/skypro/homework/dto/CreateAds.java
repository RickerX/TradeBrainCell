package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateAds {
    private String description; // описание объявления
    private Integer price;          // цена объявления
    private String title;       // заголовок объявления
//    private String image;
//    private Integer pk;
}

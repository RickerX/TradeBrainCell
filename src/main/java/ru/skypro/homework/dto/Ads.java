package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Ads {

    private long author;
    private String image;
    private long pk;
    private int price;
    private String title;
}

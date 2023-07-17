package ru.skypro.homework.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AdsComment {

    private Integer author;
    private Date createdAt;
    private Integer pk;
    private String text;
}

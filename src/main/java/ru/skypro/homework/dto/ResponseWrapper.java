package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponseWrapper {
    public int count;
    public List<Ads> results;
}

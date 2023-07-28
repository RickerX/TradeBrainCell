package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class ResponseWrapper {
    public int count;
    public List<Ads> results;
}

package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ResponseWrapperComments {
    public int count;
    public List<Comment> results;
}

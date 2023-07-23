package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponseWrapperComments {
    public int count;
    public List<Comment> results;
}

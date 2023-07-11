package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullAds {
    private String authorFirstName;
    private String image;
    private String authorLastName;
    private String phone;
    private String email;
    private Integer price;
    private Integer pk;
    private String title;
    private String description;
}

package ru.skypro.homework.dto;

import lombok.Data;
@Data
public class Comment {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;         //дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
    private Integer pk;                // id комментария
    private String text;

}

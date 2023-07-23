package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import javax.persistence.*;
import java.util.List;


@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
//@Entity
//@Table(name = "ads")
public class Ads {
    //    private String image;
//    private Integer price;
//    private Integer author;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer pk;
//    private String title;
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments;
//    @ManyToOne
//    private User adsAuthor;
//
//    public static Ads fromFull(FullAds ads) {
//        return Ads.builder()
//                .image(ads.getImage())
//                .price(ads.getPrice())
//                .title(ads.getTitle())
//                .pk(ads.getPk())
//                .build();
//    }
    private long author;   // id автора объявления
    private String image; // ссылка на картинку объявления
    private long pk;       // id объявления
    private int price;    // цена объявления
    private String title; // заголовок объявления
    //private String contentType; // тип файла image
}

package ru.skypro.homework.dto;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
public class Ads {
    private String image;
    private Integer price;
    private Integer author;
    private Integer pk;
    private String title;

    public static Ads fromFull(FullAds ads) {
        return Ads.builder()
                .image(ads.getImage())
                .price(ads.getPrice())
                .title(ads.getTitle())
                .pk(ads.getPk())
                .build();
    }
}

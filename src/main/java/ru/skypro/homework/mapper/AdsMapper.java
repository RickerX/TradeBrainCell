package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.UserEntity;

@Component
public class AdsMapper {
    public Ads entityToAdsDto(AdsEntity entity) {
        return new Ads(entity.getAuthor().getId(), entity.getImagePath(),
                entity.getPk(), entity.getPrice(), entity.getTitle());
    }

    public FullAds entityToFullAdsDto(AdsEntity entity) {
        return new FullAds(entity.getPk(), entity.getAuthor().getFirstName(), entity.getAuthor().getLastName(),
                entity.getDescription(), entity.getAuthor().getUsername(), entity.getImagePath(),
                entity.getAuthor().getPhone(), entity.getPrice(), entity.getTitle());
    }

    public AdsEntity createAdsToEntity(CreateAds ads, UserEntity author) {
        return new AdsEntity(author, ads.getTitle(), ads.getPrice(), ads.getDescription());
    }
}

package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.model.AdsModel;
import ru.skypro.homework.model.ImageModel;

import java.util.Optional;
@Component
public class AdsMapper {

    public Ads adsModelToAds(AdsModel adsModel) {
        Ads ads = new Ads();
        ads.setAuthor(adsModel.getUser().getId());
        ads.setImage(Optional.ofNullable(adsModel.getImage())
                .map(ImageModel::getPath)
                .orElse(null));
        ads.setPk(adsModel.getId());
        ads.setPrice(adsModel.getPrice());
        ads.setTitle(adsModel.getTitle());
        //ads.setContentType(adsModel.getContentType());
        return ads;
    }

    public FullAds adsModelToFullAds(AdsModel adsModel) {
        if (adsModel == null) {
            return null;
        }
        FullAds fullAds = new FullAds();
        fullAds.setPk(adsModel.getId());
        fullAds.setAuthorFirstName(adsModel.getUser().getFirstName());
        fullAds.setAuthorLastName(adsModel.getUser().getLastName());
        fullAds.setDescription(adsModel.getDescription());
        fullAds.setEmail(adsModel.getUser().getUsername());
        //fullAds.setImage(adsModel.getImage().getPath());
        fullAds.setImage(Optional.ofNullable(adsModel.getImage())
                .map(ImageModel::getPath)
                .orElse(null));
        fullAds.setPhone(adsModel.getUser().getPhone());
        fullAds.setPrice((adsModel.getPrice()));
        fullAds.setTitle(adsModel.getTitle());
        return fullAds;
    }

    public AdsModel CreateAdsToAdsModel(CreateAds createAds) {
        if (createAds == null) {
            throw new RuntimeException("Не получилось!");
        }
        AdsModel adsModel = new AdsModel();
        adsModel.setDescription(createAds.getDescription());
        adsModel.setPrice(createAds.getPrice());
        adsModel.setTitle(createAds.getTitle());
        return adsModel;
    }

    public AdsModel CreateAdsToAdsModel(AdsModel adsModel, CreateAds createAds){
        adsModel.setDescription(isNull(createAds.getDescription()) ? adsModel.getDescription() : createAds.getDescription());
        adsModel.setPrice(isNull(createAds.getPrice()) ? adsModel.getPrice() : createAds.getPrice());
        adsModel.setTitle(isNull(createAds.getTitle()) ? adsModel.getTitle() : createAds.getTitle());
        return adsModel;
    }

    private Boolean isNull(Object o) {
        return Optional.ofNullable(o).isEmpty();
    }
}

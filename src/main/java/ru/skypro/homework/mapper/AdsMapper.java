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
    //AdsMapper INSTANCE = Mappers.getMapper( AdsMapper.class );

    /*@Mapping(source = "id", target = "pk")
    @Mapping(target = "author", expression = "java(this.getUser().getId())")
    Ads adsModelToAds(AdsModel adsModel);*/

    /**
     * Mapping ads model object to ads
     * @param adsModel initial object of type AdsModel
     * @return resulting object of type Ads
     */
    public Ads adsModelToAds(AdsModel adsModel) {
        Ads ads = new Ads();
        ads.setAuthor(adsModel.getUser().getId());
        //ads.setImage(null);
        //adDto.setImage(adModel.getImage().getPath());
        ads.setImage(Optional.ofNullable(adsModel.getImage())
                .map(ImageModel::getPath)
                .orElse(null));
        ads.setPk(adsModel.getId());
        ads.setPrice(adsModel.getPrice());
        ads.setTitle(adsModel.getTitle());
        //ads.setContentType(adsModel.getContentType());
        return ads;
    }

    /**
     * Mapping ads model object to full ads
     * @param adsModel initial object of type AdsModel
     * @return resulting object of type FullAds
     */
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

    /**
     * Mapping ads to ads model object
     * @param createAds initial object of type Ads
     * @return resulting object of type AdsModel
     */
    public AdsModel CreateAdsToAdsModel(CreateAds createAds) {
        if (createAds == null) {
            throw new RuntimeException("Неясно что делать, если createAds == null");
        }
        AdsModel adsModel = new AdsModel();
        adsModel.setDescription(createAds.getDescription());
        adsModel.setPrice(createAds.getPrice());
        adsModel.setTitle(createAds.getTitle());
        return adsModel;
    }

    /**
     * Mapping ads to ads model object
     * @param adsModel ad model for edited
     * @param createAds initial object of type AdDto
     * @return resulting object of type AdModel
     */
    public AdsModel CreateAdsToAdsModel(AdsModel adsModel, CreateAds createAds){
        adsModel.setDescription(isNull(createAds.getDescription()) ? adsModel.getDescription() : createAds.getDescription());
        adsModel.setPrice(isNull(createAds.getPrice()) ? adsModel.getPrice() : createAds.getPrice());
        adsModel.setTitle(isNull(createAds.getTitle()) ? adsModel.getTitle() : createAds.getTitle());
        return adsModel;
    }

    /**
     * Checked object for null
     * @param o checked object
     * @return true if the object being checked is null
     */
    private Boolean isNull(Object o) {
        return Optional.ofNullable(o).isEmpty();
    }
}

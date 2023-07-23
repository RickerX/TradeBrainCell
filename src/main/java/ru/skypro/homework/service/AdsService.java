package ru.skypro.homework.service;

import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.model.AdsModel;
import ru.skypro.homework.model.ImageModel;

import java.util.List;

public interface AdsService {
//    List<Ads> getAllAds();
//
//    Ads addAds(CreateAds ads, String name);
//
//    List<Ads> getByEmail(String email);
//
//    FullAds getById(Integer id);
//
//    Ads updateAds(Integer id, Ads ads);
ResponseWrapper getAds();

    /**
     * Create new ads and add it to the repository
     * @param properties data for ads
     * @param image picture with ads image
     * @param userName ads creator username
     */
    Ads createAds(CreateAds properties, ImageModel image, String userName);

    /**
     * Get full information about ads
     * @param id ad's id
     * @return full info about ads
     */
    FullAds getFullAds(long id);

    /**
     * Remove ad by id
     * @param id ad's id
     * @return http code
     */
    int removeAds(long id);

    /**
     * Update ad by id
     * @param id ad's id
     * @param createAds new data for update ad
     * @return updated ad
     */
    Ads updateAds(long id, CreateAds createAds);

    /**
     * Get list of ads by user
     * @param username username
     * @return list of user ads
     */
    ResponseWrapper getAdsMe(String username);

    /**
     * Update image for ad
     * @param ads editing ad
     * @param image new image for ad
     * @return edited ad
     */
    AdsModel updateAdsImage(AdsModel ads, ImageModel image);

    /**
     * Find ad, which containing string in title
     * @param searchTitle search string
     * @return list of eligible ad
     */
    List<Ads> findByTitleContainingIgnoreCase(String searchTitle);

    /**
     * Get ad by id
     * @param id ad's id
     * @return eligible ad
     */
    AdsModel getAdById(long id);
}

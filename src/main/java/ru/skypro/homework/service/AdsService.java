package ru.skypro.homework.service;

import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.model.AdsModel;
import ru.skypro.homework.model.ImageModel;

import java.util.List;

public interface AdsService {

ResponseWrapper getAds();


    Ads createAds(CreateAds properties, ImageModel image, String userName);


    FullAds getFullAds(long id);


    int removeAds(long id);


    Ads updateAds(long id, CreateAds createAds);


    ResponseWrapper getAdsMe(String username);


    AdsModel updateAdsImage(AdsModel ads, ImageModel image);


    List<Ads> findByTitleContainingIgnoreCase(String searchTitle);


    AdsModel getAdById(long id);
}

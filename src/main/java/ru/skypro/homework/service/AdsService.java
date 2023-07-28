package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.entity.AdsEntity;

import java.io.IOException;

public interface AdsService {
    Ads add(CreateAds properties, MultipartFile image, String email) throws IOException;

    FullAds getFullAdsById(int id);

    ResponseWrapper getAllAds();

    ResponseWrapper getAllMyAds(String name);

    void delete(int id) throws IOException;

    Ads update(int id, CreateAds ads);

    AdsEntity getEntity(int id);

    void uploadImage(int id, MultipartFile image) throws IOException;
}

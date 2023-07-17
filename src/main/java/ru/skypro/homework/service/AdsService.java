package ru.skypro.homework.service;

import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.FullAds;

import java.util.List;

public interface AdsService {

    List<Ads> getAllAds();

    Ads addAds(CreateAds ads, String name);

    List<Ads> getByEmail(String email);

    FullAds getById(Integer id);

    Ads updateAds(Integer id, Ads ads);
}

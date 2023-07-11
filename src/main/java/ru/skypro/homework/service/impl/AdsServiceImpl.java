package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.service.AdsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class AdsServiceImpl implements AdsService {
    private final Map<Integer, FullAds> adsMap = new HashMap<>();

    @Override
    public List<Ads> getAllAds() {
        return adsMap.values().stream()
                .map(Ads::fromFull)
                .collect(Collectors.toList());
    }

    @Override
    public Ads addAds(CreateAds ads, String email) {
        FullAds fullAds = FullAds.builder()
                .email(email)
                .image(ads.getImage())
                .price(ads.getPrice())
                .title(ads.getTitle())
                .description(ads.getDescription())
                .pk(ads.getPk())
                .build();
        adsMap.put(ads.getPk(), fullAds);
        return Ads.fromFull(fullAds);
    }

    @Override
    public List<Ads> getByEmail(String email) {
        return adsMap.values().stream()
                .filter(t -> t.getEmail().equals(email))
                .map(Ads::fromFull)
                .collect(Collectors.toList());
    }

    @Override
    public FullAds getById(Integer id) {
        return adsMap.get(id);
    }

    @Override
    public Ads updateAds(Integer id, Ads ads) {
        FullAds fullAds = adsMap.get(id);
        fullAds.setImage(ads.getImage());
        fullAds.setPrice(ads.getPrice());
        fullAds.setTitle(ads.getTitle());
        fullAds.setPk(ads.getPk());
        return Ads.fromFull(fullAds);
    }
}

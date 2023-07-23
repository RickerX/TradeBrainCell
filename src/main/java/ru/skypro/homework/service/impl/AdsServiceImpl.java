package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.AdsModel;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final UserServiceImpl userService;

    private final AdsMapper adsMapper;

    public AdsServiceImpl(AdsRepository adsRepository, UserServiceImpl userService, AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.userService = userService;
        this.adsMapper = adsMapper;
    }

    public ResponseWrapper getAds() {
        //List<Ads> ads = new ArrayList<>();
        List<AdsModel> adsModels = adsRepository.findAll();
        ResponseWrapper wrapperAds = new ResponseWrapper();
        wrapperAds.setCount(adsModels.size());
        wrapperAds.setResults(
                adsModels.stream()
                        .map(adsMapper::adsModelToAds)
                        .collect(Collectors.toList())
        );
        //return AdsMapper.INSTANCE.adsModelToAds(a);
        return wrapperAds;
    }

    @Override
    public Ads createAds(CreateAds properties, ImageModel image, String userName) {
        AdsModel adsModel = adsMapper.CreateAdsToAdsModel(properties);
        adsModel.setImage(image);
        adsModel.setUser(userService.getUser(userName));
        return adsMapper.adsModelToAds(adsRepository.save(adsModel));
    }

    @Override
    public FullAds getFullAds(long id) {
        AdsModel adsModel = adsRepository.findById(id).orElse(null);
        return adsMapper.adsModelToFullAds(adsModel);
    }

    @Override
    public int removeAds(long id) {
        if (adsRepository.findById(id).isEmpty()) {
            return 204; // не найден
        } else {
            adsRepository.deleteById(id);
            return 0; // запись удалена
        }
    }

    @Override
    public Ads updateAds(long id, CreateAds createAds) {
        Optional<AdsModel> adsModelOptional = adsRepository.findById(id);
        if (adsModelOptional.isEmpty()) {
            return null;
        }
        AdsModel adsModel = adsMapper.CreateAdsToAdsModel(adsModelOptional.get(), createAds);
        adsRepository.save(adsModel);
        return adsMapper.adsModelToAds(adsModel);
    }

    @Override
    public ResponseWrapper getAdsMe(String username) {
        UserModel user = userService.getUser(username);
        List<AdsModel> adModels = adsRepository.findAdsModelByUserId(user.getId());
        ResponseWrapper wrapperAds = new ResponseWrapper();
        wrapperAds.setCount(adModels.size());
        wrapperAds.setResults(
                adModels.stream()
                        .map(adsMapper::adsModelToAds)
                        .collect(Collectors.toList())
        );
        return wrapperAds;
//        return adsModels.stream().map(adsMapper::adsModelToAds).collect(Collectors.toList());
    }

    @Override
    public AdsModel updateAdsImage(AdsModel ads, ImageModel image) {
        image.setId(Optional.ofNullable(ads.getImage())
                .map(ImageModel::getId)
                .orElse(null));


        //ads.getImage().getId());
        ads.setImage(image);
        return adsRepository.saveAndFlush(ads);
    }

    @Override
    public List<Ads> findByTitleContainingIgnoreCase(String searchTitle) {
        List<AdsModel> adModels = adsRepository.findByTitleLikeIgnoreCase("%" + searchTitle + "%");
        return adModels.stream().map(adsMapper::adsModelToAds).collect(Collectors.toList());
    }

    @Override
    public AdsModel getAdById(long id) {
        return adsRepository.findById(id).orElse(null);
    }
}

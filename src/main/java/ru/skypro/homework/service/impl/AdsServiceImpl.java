package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final AdsMapper mapper;

    @Override
    public Ads add(CreateAds properties, MultipartFile image, String email) throws IOException {
        AdsEntity ad = mapper.createAdsToEntity(properties, userService.getEntity(email));
        ad.setImage(imageService.saveImage(image));
        return mapper.entityToAdsDto(adsRepository.save(ad));
    }

    @Override
    public FullAds getFullAdsById(int id) {
        return mapper.entityToFullAdsDto(getEntity(id));
    }

    @Override
    public void delete(int id) throws IOException {
        ImageEntity image = getEntity(id).getImage();
        adsRepository.deleteById(id);
        imageService.deleteImage(image);
    }

    @Override
    public Ads update(int id, CreateAds ads) {
        AdsEntity entity = getEntity(id);
        entity.setTitle(ads.getTitle());
        entity.setDescription(ads.getDescription());
        entity.setPrice(ads.getPrice());
        adsRepository.save(entity);
        return mapper.entityToAdsDto(entity);
    }

    @Override
    public AdsEntity getEntity(int id) {
        return adsRepository.findById(id).orElseThrow(() -> new FindNoEntityException("объявление"));
    }

    @Override
    public void uploadImage(int id, MultipartFile image) throws IOException {
        AdsEntity adEntity = getEntity(id);
        ImageEntity imageEntity = adEntity.getImage();
        adEntity.setImage(imageService.saveImage(image));
        adsRepository.save(adEntity);
        if (imageEntity != null) {
            imageService.deleteImage(imageEntity);
        }
    }

    @Override
    public ResponseWrapper getAllAds() {
        return getWrapper(adsRepository.findAll());
    }

    @Override
    public ResponseWrapper getAllMyAds(String username) {
        return getWrapper(adsRepository.findAllByAuthorUsername(username));
    }

    private ResponseWrapper getWrapper(List<AdsEntity> list) {
        List<Ads> result = new LinkedList<>();
        list.forEach((entity -> result.add(mapper.entityToAdsDto(entity))));
        return new ResponseWrapper(result.size(), result);
    }
}

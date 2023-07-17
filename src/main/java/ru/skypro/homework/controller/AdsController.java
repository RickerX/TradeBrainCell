package ru.skypro.homework.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.service.AdsService;

import java.util.List;

@Api(tags = "Объявления")
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdsService adsService;

    @GetMapping()
    public ResponseWrapper<Ads> getALLAds(@RequestParam MultipartFile file) {
        var adsResponseWrapper = new ResponseWrapper<Ads>();
        List<Ads> allAds = adsService.getAllAds();
        adsResponseWrapper.setResults(allAds);
        adsResponseWrapper.setCount(allAds.size());
        return adsResponseWrapper;
    }

    @PostMapping()
    public Ads addAds(@RequestBody CreateAds createAds, Authentication authentication) {
        return adsService.addAds(createAds, authentication.getName());
    }

    @GetMapping("me")
    public ResponseWrapper<Ads> getAdsMe(Authentication authentication) {
        var adsResponseWrapper = new ResponseWrapper<Ads>();
        List<Ads> allAds = adsService.getByEmail(authentication.getName());
        adsResponseWrapper.setResults(allAds);
        adsResponseWrapper.setCount(allAds.size());
        return adsResponseWrapper;
    }

    @GetMapping("/{id}")
    public FullAds getAds(@PathVariable("id") Integer id) {
        return adsService.getById(id);
    }

    @PatchMapping("/{id}")
    public Ads updateAds(@PathVariable("id") Integer id,
                         @RequestBody Ads ads) {
        return adsService.updateAds(id, ads);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void removeAds(@PathVariable("id") Integer id) {
    }

    @GetMapping("/{ad_pk}/comment")
    public ResponseWrapper<AdsComment> getAdsComments(@PathVariable("ad_pk") String adPk) {
        return new ResponseWrapper<>();
    }

    @PostMapping("/{ad_pk}/comment")
    public AdsComment addAdsComments(@PathVariable("ad_pk") String adPk,
                                     @RequestBody AdsComment comment) {
        return new AdsComment();
    }

    @GetMapping("/{ad_pk}/comment/{id}")
    public AdsComment getAdsComment(@PathVariable("ad_pk") String adPk,
                                    @PathVariable("id") Integer id) {
        return new AdsComment();
    }

    @PatchMapping("/{ad_pk}/comment/{id}")
    public AdsComment updateAdsComment(@PathVariable("ad_pk") String adPk,
                                       @PathVariable("id") Integer id,
                                       @RequestBody AdsComment comment) {
        return new AdsComment();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{ad_pk}/comment/{id}")
    public void deleteAdsComment(@PathVariable("ad_pk") String adPk,
                                 @PathVariable("id") Integer id) {
    }

}


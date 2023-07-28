package ru.skypro.homework.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {
    private final AdsService adsService;
    private final ImageService imageService;


    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ads> addAd(@RequestPart CreateAds properties, @RequestPart MultipartFile image,
                                     Authentication auth) throws IOException {
        return ResponseEntity.ok(adsService.add(properties, image, auth.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable int id) {
        return ResponseEntity.ok(adsService.getFullAdsById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@adsServiceImpl.getEntity(#id).author.username.equals(#auth.name) or hasAuthority('DELETE_ANY_AD')")
    public ResponseEntity<?> removeAd(@PathVariable int id, Authentication auth) throws IOException {
        adsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@adsServiceImpl.getEntity(#id).author.username.equals(#auth.name) or hasAuthority('UPDATE_ANY_AD')")
    public ResponseEntity<Ads> updateAds(@PathVariable int id, @RequestBody CreateAds ads, Authentication auth) {
        return ResponseEntity.ok(adsService.update(id, ads));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper> getAdsMe(Authentication auth) {
        return ResponseEntity.ok(adsService.getAllMyAds(auth.getName()));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable int id, @RequestPart MultipartFile image) throws IOException {
        adsService.uploadImage(id, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) throws IOException {
        long imageId = adsService.getEntity(id).getImage().getId();
        return ResponseEntity.ok(imageService.getImage(imageId));
    }
}

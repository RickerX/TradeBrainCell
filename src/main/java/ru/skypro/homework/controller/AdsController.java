package ru.skypro.homework.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.AdsModel;
import ru.skypro.homework.model.ImageModel;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
@Api(tags = "Объявления")
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
//@RequiredArgsConstructor
public class AdsController {
    private final AdsService adsService;
    private final ImageService imageService;
    private final UserService userService;

    public AdsController(AdsService adsService, ImageService imageService, UserService userService) {
        this.adsService = adsService;
        this.imageService = imageService;
        this.userService = userService;
    }

    @Operation(
            tags = "Объявления",
            summary = "Получить все объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapper.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<?> getAds() {
        ResponseWrapper rs = adsService.getAds();
        return ResponseEntity.ok(rs);
    }


    @Operation(
            tags = "Объявления",
            summary = "Добавить объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    @SneakyThrows
    @Transactional
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAds(@RequestPart("properties") CreateAds properties,
                                      @RequestPart("image") MultipartFile imageFile,
                                      Authentication authentication) {

        ImageModel uploadedImage = imageService.upload(imageFile, 400F);
        ImageModel savedImage = imageService.save(uploadedImage);

        Ads ads = adsService.createAds(properties, savedImage, authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(ads);
    }


    @Operation(
            tags = "Объявления",
            summary = "Получить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FullAds.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getFullAd(@PathVariable long id, Authentication authentication) {
        if (null == authentication) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        FullAds fullAds = adsService.getFullAds(id);
        return ResponseEntity.ok(fullAds);

    }


    @Operation(
            tags = "Объявления",
            summary = "Удалить объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable long id, Authentication authentication) {
        ResponseEntity<?> response = accessResponse(authentication, id);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            adsService.removeAds(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


    @Operation(
            tags = "Объявления",
            summary = "Обновить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    )
            }
    )
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAds(@PathVariable long id, @RequestBody CreateAds createAds, Authentication authentication) {

        ResponseEntity<?> response = accessResponse(authentication, id);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            Ads ads = adsService.updateAds(id, createAds);
            return ResponseEntity.ok(ads);
        }
    }

    private ResponseEntity<?> accessResponse(Authentication authentication, Long id) {
        AdsModel adsModel = adsService.getAdById(id);

        if (adsModel == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else if (adsModel.getUser().getUsername().equals(authentication.getName()) ||
                userService.getUser(authentication.getName()).getRole() == Role.ADMIN) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @Operation(
            tags = "Объявления",
            summary = "Получить объявления авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapper.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )

    @GetMapping("me")
    public ResponseEntity<?> getAdsMe(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            ResponseWrapper rw = adsService.getAdsMe(authentication.getName());
            return ResponseEntity.ok(rw);
        }
    }


    @Operation(
            tags = "Объявления",
            summary = "Обновить картинку объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    )
            }
    )
    @SneakyThrows
    @Transactional
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable Long id,
                                           @RequestPart("image") MultipartFile imageFile,
                                           Authentication authentication) {

        ResponseEntity<?> response = accessResponse(authentication, id);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            ImageModel uploadImageModel = imageService.upload(imageFile, 400F);
            AdsModel adsModel = adsService.getAdById(id);
            if (null != uploadImageModel) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(uploadImageModel.getMediaType()));
                headers.setContentLength(uploadImageModel.getImage().length);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .headers(headers)
                        .body(adsService
                                .updateAdsImage(adsModel, uploadImageModel).getImage().getImage());
            } else {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }

    }

    @GetMapping("/findByTitle/{searchTitle}")
    public ResponseEntity<?> searchAds(@PathVariable String searchTitle) {
        return ResponseEntity.ok(
                adsService.findByTitleContainingIgnoreCase(searchTitle)
        );
    }

}

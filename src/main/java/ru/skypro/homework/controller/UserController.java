package ru.skypro.homework.controller;
import io.swagger.annotations.Api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import javax.transaction.Transactional;

@Api(tags = "Пользователи")
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
//@RequiredArgsConstructor

public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final ImageService imageService;

    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @Operation(
            tags = "Пользователи",
            summary = "Получить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication) {
        User authUser = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(authUser);
    }

    @Operation(
            tags = "Пользователи",
            summary = "Обновить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody User user, Authentication authentication) {
        UserModel authUser = userService.getUser(authentication.getName());
        if (user.getId() != authUser.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.updateUser(authUser, user);
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Пользователи",
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
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
    @PostMapping("/set_password")
    public ResponseEntity<?> setUserPassword(@RequestBody NewPassword newPassword, Authentication authentication) {
        UserModel user = userService.getUser(authentication.getName());

        if (!userService.isPasswordCorrect(user, newPassword.currentPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User modifiedUser = userService.setUserPassword(user, newPassword);
        return ResponseEntity.ok(modifiedUser);
    }

    @Operation(
            tags = "Пользователи",
            summary = "Обновить аватар авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )
    @SneakyThrows
    @Transactional
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> loadUserImage(@RequestPart("image") MultipartFile image, Authentication authentication) {
        UserModel authUser = userService.getUser(authentication.getName());
        User user = userService.loadUserImage(authUser, imageService.upload(image, 200F));
        //return ResponseEntity.ok(userDto);
        return ResponseEntity.ok().build();
    }
//    @PostMapping
//    public CreateUser addUser(@RequestBody CreateUser user) {
//        return new CreateUser();
//    }
//
//    @GetMapping("/me")
//    public ResponseWrapper<User> getUsers() {
//        return new ResponseWrapper<>();
//    }
//
//    @PatchMapping("/me")
//    public User updateUser(@RequestBody User user) {
//        return new User();
//    }
//
//    @GetMapping("/{id}")
//    public User getUser(@PathVariable("id") Integer id) {
//        return new User();
//    }
//
//    @PostMapping("/set_password")
//    public NewPassword setPassword(@RequestBody NewPassword newPassword) {
//        return new NewPassword();
//    }
//

}

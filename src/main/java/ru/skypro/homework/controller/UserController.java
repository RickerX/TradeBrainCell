package ru.skypro.homework.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import java.io.IOException;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final ImageService imageService;

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword,
                                         Authentication authentication) {
        if (authService.setPassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(Authentication authentication) {
        return ResponseEntity.ok( userService.get(authentication.getName()));
    }

    @PatchMapping("/me")
    public ResponseEntity<User> updateUser(@RequestBody User newUser, Authentication authentication) {
        return ResponseEntity.ok(userService.update(newUser, authentication.getName()));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestPart MultipartFile image, Authentication auth) throws IOException {
        userService.uploadImage(image, auth.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) throws IOException {
        long imageId = userService.getEntityById(id).getImage().getId();
        return ResponseEntity.ok(imageService.getImage(imageId));
    }
}

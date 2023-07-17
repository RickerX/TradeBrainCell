package ru.skypro.homework.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.pass.NewPassword;

@Api(tags = "Пользователи")
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    @PostMapping
    public CreateUser addUser(@RequestBody CreateUser user) {
        return new CreateUser();
    }

    @GetMapping("/me")
    public ResponseWrapper<User> getUsers() {
        return new ResponseWrapper<>();
    }

    @PatchMapping("/me")
    public User updateUser(@RequestBody User user) {
        return new User();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return new User();
    }

    @PostMapping("/set_password")
    public NewPassword setPassword(@RequestBody NewPassword newPassword) {
        return new NewPassword();
    }
}

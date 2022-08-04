package ru.monsterdev.study.otusnet.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.monsterdev.study.otusnet.models.SocialUser;
import ru.monsterdev.study.otusnet.services.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public boolean registerUser(@Valid @RequestBody SocialUser user) {
        return userService.register(user);
    }

    @GetMapping("/{id}")
    public SocialUser showUserInfo(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }
}

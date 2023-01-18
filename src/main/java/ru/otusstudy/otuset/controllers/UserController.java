package ru.otusstudy.otuset.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otusstudy.otuset.models.dto.requests.CreateUserDto;
import ru.otusstudy.otuset.services.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public String createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("Создание пользователя: {}", createUserDto);
        return userService.createUser(createUserDto)
                .map(id -> "id" + id)
                .orElseThrow(() -> new RuntimeException("Пользователь не создан"));
    }
}

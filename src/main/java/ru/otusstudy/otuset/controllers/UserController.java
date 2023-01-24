package ru.otusstudy.otuset.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otusstudy.otuset.models.dto.requests.CreateUserDto;
import ru.otusstudy.otuset.models.dto.responses.UserDto;
import ru.otusstudy.otuset.services.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Получение списка пользователей");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        log.info("Получение пользователя {}", id);
        return userService.getById(id);
    }

    @PostMapping("/create")
    public String createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("Создание пользователя: {}", createUserDto);
        return "id" + userService.createUser(createUserDto);
    }
}

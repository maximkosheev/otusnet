package ru.otusstudy.otuset.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import ru.otusstudy.otuset.exeptions.EntityNotFoundException;
import ru.otusstudy.otuset.models.dto.requests.CreateUserDto;
import ru.otusstudy.otuset.models.dto.responses.UserDto;
import ru.otusstudy.otuset.services.UserService;

import javax.validation.Valid;
import java.util.List;

import static java.lang.String.format;

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
        try {
            return userService.getById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format("User %d not found", id));
        }
    }

    @PostMapping("/create")
    public String createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("Создание пользователя: {}", createUserDto);
        return "id" + userService.createUser(createUserDto);
    }

    @GetMapping("/search")
    public List<UserDto> searchUsers(
            @RequestParam(value = "first_name") String firstName,
            @RequestParam(value = "last_name") String lastName) {
        log.info("Поиск пользователей по firstName: {}, lastName: {}", firstName, lastName);
        return userService.searchByName(firstName, lastName);
    }
}

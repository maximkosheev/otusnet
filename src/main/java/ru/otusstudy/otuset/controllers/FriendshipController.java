package ru.otusstudy.otuset.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.services.FriendshipService;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/create")
    public void create(@AuthenticationPrincipal OtusetUser user, @RequestBody @NotNull Long friendId) {
        log.info("Создание связи между {} и {}", user.getId(), friendId);
        friendshipService.create(user.getId(), friendId);
    }
}

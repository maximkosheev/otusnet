package ru.otusstudy.otuset.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.services.PostService;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public Long publishPost(@AuthenticationPrincipal OtusetUser user, @RequestBody String content) {
        return postService.publishPost(user, content);
    }
}

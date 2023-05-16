package ru.otusstudy.otuset.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.models.dto.responses.PostDto;
import ru.otusstudy.otuset.services.FeedService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping()
    public List<PostDto> getFeed(@AuthenticationPrincipal OtusetUser user) {
        return feedService.getFeed(user.getId());
    }

}

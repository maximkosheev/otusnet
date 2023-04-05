package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.models.dto.responses.PostDto;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    public List<PostDto> getFeed(Long userId) {
        return Collections.emptyList();
    }
}

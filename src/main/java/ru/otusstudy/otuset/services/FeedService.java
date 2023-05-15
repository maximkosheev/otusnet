package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.models.dto.responses.PostDto;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    public static final String FEED_KEY = "feed:{id}";

    private final RedisTemplate<String, Long> redisTemplate;
    private final PostCache postCache;
    private ListOperations<String, Long> feedOperation;

    @PostConstruct
    private void init() {
        feedOperation = redisTemplate.opsForList();
    }

    private String feedKey(Long id) {
        return FEED_KEY.replace("{id}", String.valueOf(id));
    }

    /**
     * Добавляет идентификатор новости {@code postId} в ленту пользователей {@code userIds}
     */
    public void addPostToFeeds(Collection<Long> userIds, Long postId) {
        userIds.stream()
                .filter(Objects::nonNull)
                .forEach(userId -> feedOperation.leftPush(feedKey(userId), postId));
    }

    public List<PostDto> getFeed(Long userId) {
        return Optional.ofNullable(feedOperation.range(feedKey(userId), 0, 1000))
                .orElse(Collections.emptyList())
                .stream()
                .map(postId -> PostDto.builder()
                        .id(postId)
                        .data(postCache.get(postId))
                        .build())
                .collect(Collectors.toList());
    }
}

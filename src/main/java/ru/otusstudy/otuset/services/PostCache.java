package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostCache {
    public static final String POST_KEY = "post:{id}";

    private final RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> postOperation;

    @PostConstruct
    private void init() {
        postOperation = redisTemplate.opsForValue();
    }

    private String postKey(Long id) {
        return POST_KEY.replace("{id}", String.valueOf(id));
    }


    public void put(Long postId, String content) {
        postOperation.set(postKey(postId), content);
    }

    public String get(Long postId) {
        return postOperation.get(postKey(postId));
    }
}

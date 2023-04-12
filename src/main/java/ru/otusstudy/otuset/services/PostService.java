package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.dao.PostDao;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.exeptions.ServiceException;
import ru.otusstudy.otuset.models.dto.responses.PostDto;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    public static final String POST_KEY = "post:{id}";
    public static final String FEED_KEY = "feed:{id}";

    private final PostDao dao;
    private final FriendshipService friendshipService;
    private final ValueOperations<String, String> postOperation;
    private final ListOperations<String, Long> feedOperation;

    private String postKey(Long id) {
        return POST_KEY.replace("{id}", String.valueOf(id));
    }

    private String feedKey(Long id) {
        return FEED_KEY.replace("{id}", String.valueOf(id));
    }

    private void putPostToFeeds(OtusetUser author, Long postId, String content) {
        try {
            postOperation.set(postKey(postId), content);
            // для всех друзей автора (подписчиков) добавляем идентификатор вновь созданного поста
            friendshipService.getFriendshipByUserId(author.getId()).getFriends()
                    .forEach(friendId -> feedOperation.leftPush(feedKey(friendId), postId));
        } catch (Exception ex) {
            log.error("Ошибкак при записи поста в кэш");
        }
    }

    public Long publishPost(OtusetUser author, String content) {
        try {
            var postId = dao.createPost(author.getId(), content);
            putPostToFeeds(author, postId, content);
            return postId;
        } catch (SQLException ex) {
            throw new ServiceException("Не смогли опубликовать пост");
        }
    }

    public String getPostById(Long postId) {
        return postOperation.get(postKey(postId));
    }

    public List<PostDto> getFeed(Long userId) {
        return Optional.ofNullable(feedOperation.range(feedKey(userId), 0, 100))
                .orElse(Collections.emptyList())
                .stream()
                .map(postId -> PostDto.builder()
                        .data(getPostById(postId))
                        .build())
                .collect(Collectors.toList());
    }
}

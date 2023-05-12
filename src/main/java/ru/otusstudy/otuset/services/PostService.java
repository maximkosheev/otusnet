package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.dao.PostDao;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.exeptions.ServiceException;

import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao dao;
    private final PostCache postCache;
    private final FriendshipService friendshipService;
    private final FeedService feedService;

    private void putPostToFeeds(OtusetUser author, Long postId, String content) {
        try {
            postCache.put(postId, content);
            feedService.addPostToFeeds(friendshipService.getFriendshipByUserId(author.getId()).getFriends(), postId);
        } catch (Exception ex) {
            log.error("Ошибкак при записи поста в кэш");
        }
    }

    public Long publishPost(OtusetUser author, String content) {
        try {
            // созднаем запись новости в БД
            var postId = dao.createPost(author.getId(), content);
            // размещаем новость в ленте
            putPostToFeeds(author, postId, content);
            return postId;
        } catch (SQLException ex) {
            throw new ServiceException("Не смогли опубликовать пост");
        }
    }
}

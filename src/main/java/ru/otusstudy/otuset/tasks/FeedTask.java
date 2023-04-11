package ru.otusstudy.otuset.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.services.FriendshipService;
import ru.otusstudy.otuset.services.PostService;
import ru.otusstudy.otuset.services.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedTask {

    private final UserService userService;
    private final FriendshipService friendshipService;
    private final PostService postService;

    void updateFeeds() {
        log.info("Обновление ленты новостей всех пользователей");
        userService.getAll().forEach(userDto -> {
            var friendship = friendshipService.getFriendshipByUserId(userDto.getId());
            friendship.getFriends().forEach(friendId -> {
                postService.getLastPosts(friendId);
            });
        });
    }
}

package ru.otusstudy.otuset.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.services.FriendshipService;

/**
 * Периодически обновляет в кэше список друзей каждого пользователя
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FriendsTask {

    private final FriendshipService friendshipService;

    @Scheduled(fixedDelay = 1 * 60 * 1000, initialDelay = 60 * 1000)
    public void updateFriendships() {
        log.info("Обновление списка друзей пользователей");
        friendshipService.updateFriendshipsCache();
    }
}

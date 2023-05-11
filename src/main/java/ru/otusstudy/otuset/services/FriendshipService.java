package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.dao.FriendshipDao;
import ru.otusstudy.otuset.domain.Friendship;
import ru.otusstudy.otuset.exeptions.ServiceException;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipService {
    private static final String FRIENDSHIP_KEY = "friendship:{id}";

    private final FriendshipDao dao;

    private final RedisTemplate<String, Long> redisTemplate;

    private SetOperations<String, Long> userFriends;

    @PostConstruct
    private void init() {
        userFriends = redisTemplate.opsForSet();
    }

    private String friendshipKey(Long id) {
        return FRIENDSHIP_KEY.replace("{id}", String.valueOf(id));
    }

    public List<Friendship> findAll() {
        try {
            return dao.findAll();
        } catch (SQLException ex) {
            throw new ServiceException("Ошибка при получении списка всех друзей");
        }
    }

    public List<Long> findByUserId(Long userId) {
        try {
            return dao.findById(userId);
        } catch (SQLException ex) {
            throw new ServiceException("Ошибка при получении списка всех друзей пользователя");
        }
    }

    public Long create(Long userId, Long friendId) {
        try {
            return dao.create(userId, friendId);
        } catch (SQLException ex) {
            throw new ServiceException("Ошибка при создании связи пользователей");
        }
    }

    public void updateFriendshipsCache() {
        findAll().forEach(friendship -> {
            Long[] ids = new Long[friendship.getFriends().size()];
            userFriends.add(friendshipKey(friendship.getId()), friendship.getFriends().toArray(ids));
        });
    }

    public Friendship getFriendshipByUserId(Long userId) {
        return Friendship.builder()
                .userId(userId)
                .friends(userFriends.intersect(Collections.singleton(friendshipKey(userId))))
                .build();
    }
}

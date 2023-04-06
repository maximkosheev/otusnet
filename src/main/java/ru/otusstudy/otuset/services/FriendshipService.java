package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.dao.FriendshipDao;
import ru.otusstudy.otuset.domain.Friendship;
import ru.otusstudy.otuset.exeptions.ServiceException;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipDao dao;

    public List<Friendship> getAll() {
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
}

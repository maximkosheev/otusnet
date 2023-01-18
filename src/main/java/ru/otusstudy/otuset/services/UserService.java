package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.dao.OtusetUserDao;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.mappers.UserMapper;
import ru.otusstudy.otuset.models.dto.requests.CreateUserDto;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final OtusetUserDao userDao;
    private final UserMapper userMapper;

    public Optional<Long> createUser(CreateUserDto createUserDto) {
        try {
            OtusetUser newUser = userMapper.toOtusetUser(createUserDto);
            return Optional.of(userDao.createUser(newUser));
        } catch (SQLException ex) {
            log.error("Error occurred while creating user", ex);
            return Optional.empty();
        }
    }
}

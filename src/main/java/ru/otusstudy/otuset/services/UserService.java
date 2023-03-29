package ru.otusstudy.otuset.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otusstudy.otuset.dao.OtusetUserDao;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.exeptions.EntityNotFoundException;
import ru.otusstudy.otuset.exeptions.ServiceException;
import ru.otusstudy.otuset.mappers.UserMapper;
import ru.otusstudy.otuset.models.dto.requests.CreateUserDto;
import ru.otusstudy.otuset.models.dto.responses.UserDto;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final OtusetUserDao userDao;
    private final UserMapper userMapper;

    @Override
    public OtusetUser loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userDao.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException(format("User %s not found", username)));
        } catch (SQLException ex) {
            throw new ServiceException("Ошибка при чтении данных пользователя");
        }
    }

    public List<UserDto> getAll() {
        try {
            return userDao.getAllUsers().stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        } catch (SQLException ex) {
            throw new ServiceException("Ошибка при получении списка пользователей");
        }
    }

    public UserDto getById(Long id) {
        try {
            return userDao.findById(id)
                    .map(userMapper::toUserDto)
                    .orElseThrow(() -> new EntityNotFoundException(OtusetUser.class, id.toString()));
        } catch (SQLException ex) {
            throw new ServiceException("Ошибка при получении пользователя");
        }
    }

    public Long createUser(CreateUserDto createUserDto) {
        try {
            OtusetUser newUser = userMapper.toOtusetUser(createUserDto);
            return userDao.createUser(newUser);
        } catch (SQLException ex) {
            throw new ServiceException("Ошибка при создании пользователя");
        }
    }

    public List<UserDto> searchByName(String firstName, String lastName) {
        try {
            return userDao.findByFIO(firstName, lastName).stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        } catch (SQLException ex) {
            throw new ServiceException("Ошибка при поиске пользователей");
        }
    }
}

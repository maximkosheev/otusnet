package ru.monsterdev.study.otusnet.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.monsterdev.study.otusnet.dao.UserDao;
import ru.monsterdev.study.otusnet.models.SocialUser;
import ru.monsterdev.study.otusnet.services.UserService;

import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SocialUser> optSocialUser = userDao.findUserByLogin(username);
        if (optSocialUser.isEmpty()) {
            throw new UsernameNotFoundException(format("Пользователь %s не найден", username));
        }
        return optSocialUser.get();
    }

    @Override
    public boolean register(SocialUser user) {
        log.info("Регистрация пользователя: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.registerUser(user).getId() != null;
    }

    @Override
    public SocialUser getUser(Long id) {
        return null;
    }

}

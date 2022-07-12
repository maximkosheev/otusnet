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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    @Override
    public boolean register(SocialUser user) {
        log.info("Регистрация пользователя: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.registerUser(user).getId() != null;
    }

}

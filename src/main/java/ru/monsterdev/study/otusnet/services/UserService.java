package ru.monsterdev.study.otusnet.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.monsterdev.study.otusnet.models.SocialUser;

public interface UserService extends UserDetailsService {
    /**
     * Регистрация нового пользователя в сети
     * @param user данные пользователя
     * @return признак успешности регистрации
     */
    boolean register(SocialUser user);
}

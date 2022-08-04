package ru.monsterdev.study.otusnet.services;

import ru.monsterdev.study.otusnet.models.AuthenticationInfo;

public interface JwtService {
    String buildAuthenticationToken(AuthenticationInfo authenticationInfo);
}

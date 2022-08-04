package ru.monsterdev.study.otusnet.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.monsterdev.study.otusnet.config.SecurityProperties;
import ru.monsterdev.study.otusnet.models.AuthenticationInfo;
import ru.monsterdev.study.otusnet.services.JwtService;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final SecurityProperties securityProperties;

    private Key getSignKey() {
        return new SecretKeySpec(securityProperties.getSecret().getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    @Override
    public String buildAuthenticationToken(AuthenticationInfo authenticationInfo) {
        Date now = new Date();
        Date exp = Date.from(now.toInstant().plusSeconds(securityProperties.getAccessTokenExpirationPeriod()));

        Map<String, Object> claims = new HashMap<>(authenticationInfo.toMap());
        claims.put("exp", exp);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignKey())
                .compact();
    }
}

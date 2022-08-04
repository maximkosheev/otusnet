package ru.monsterdev.study.otusnet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private String signKey;
    private Long accessTokenExpirationPeriod;
    private String secret;
}

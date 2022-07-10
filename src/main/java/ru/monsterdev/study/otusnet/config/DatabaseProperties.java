package ru.monsterdev.study.otusnet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "db")
public class DatabaseProperties {
    private String url;
    private String databaseName;
    private String username;
    private String password;
}

package ru.otusstudy.otuset.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "datasource")
public class DatabaseProperties {
    private String url;
    private String username;
    private String password;
}

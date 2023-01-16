package ru.otusstudy.otuset.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otusstudy.otuset.config.properties.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    private final DatabaseProperties dbProperties;

    @Bean
    public Connection dbConnection() throws SQLException {
        return DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUsername(),
                dbProperties.getPassword());
    }
}

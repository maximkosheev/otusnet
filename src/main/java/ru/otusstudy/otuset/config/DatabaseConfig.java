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
    public Connection masterConnection() throws SQLException {
        return DriverManager.getConnection(
                dbProperties.getMaster().getUrl(),
                dbProperties.getMaster().getUsername(),
                dbProperties.getMaster().getPassword());
    }

    @Bean
    public Connection slaveConnection() throws SQLException {
        return DriverManager.getConnection(
                dbProperties.getSlave().getUrl(),
                dbProperties.getSlave().getUsername(),
                dbProperties.getSlave().getPassword());
    }
}

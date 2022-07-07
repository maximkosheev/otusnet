package ru.monsterdev.study.otusnet.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DbConfig {

    private final DatabaseProperties databaseProperties;

    @Bean
    public DataSource dataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUser(databaseProperties.getUsername());
        ds.setPassword(databaseProperties.getPassword());
        return ds;
    }
}

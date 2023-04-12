package ru.otusstudy.otuset.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostDao {

    public Long createPost(Long authorId, String content) throws SQLException {
        return 0L;
    }
}

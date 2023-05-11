package ru.otusstudy.otuset.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostDao {
    private static final String CREATE = "INSERT INTO post(author_id, data) VALUES(?, ?)";

    private final Connection masterConnection;

    public Long createPost(Long authorId, String content) throws SQLException {
        try (PreparedStatement stmt = masterConnection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, authorId);
            stmt.setString(2, content);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.first();
            return rs.getLong(1);
        } catch (SQLException ex) {
            log.error("", ex);
            throw ex;
        }
    }
}

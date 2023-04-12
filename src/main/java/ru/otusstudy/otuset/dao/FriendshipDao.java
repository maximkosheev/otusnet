package ru.otusstudy.otuset.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.otusstudy.otuset.domain.Friendship;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendshipDao {

    private static final String FIND_ALL = "SELECT id, user_id, GROUP_CONCAT(friend_id) as friends from friendship GROUP BY user_id";
    private static final String FIND_BY_ID = "SELECT friend_id from friendship WHERE user_id = ?";
    private static final String CREATE = "INSERT INTO friendship(user_id, friend_id) VALUES(? ?)";

    private final Connection masterConnection;
    private final Connection slaveConnection;

    private Friendship buildFromResultSet(ResultSet rs) throws SQLException {
        List<String> friendIds = Optional.ofNullable(rs.getString("friends"))
                .map(ids -> List.of(ids.split(",")))
                .orElse(Collections.emptyList());

        return Friendship.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .friends(friendIds.stream().map(Long::getLong).collect(Collectors.toSet()))
                .build();
    }

    public List<Friendship> findAll() throws SQLException {
        try (Statement stmt = slaveConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(FIND_ALL);
            List<Friendship> result = new ArrayList<>();
            while (rs.next()) {
                result.add(buildFromResultSet(rs));
            }
            return result;
        } catch (SQLException ex) {
            log.error("", ex);
            throw ex;
        }
    }

    public List<Long> findById(Long userId) throws SQLException {
        try (PreparedStatement stmt = slaveConnection.prepareStatement(FIND_BY_ID)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<Long> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getLong("friend_id"));
            }
            return result;
        } catch (SQLException ex) {
            log.error("", ex);
            throw ex;
        }
    }

    public Long create(Long userId, Long friendId) throws SQLException {
        try (PreparedStatement stmt = masterConnection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, friendId);
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

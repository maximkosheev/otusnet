package ru.otusstudy.otuset.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.otusstudy.otuset.domain.OtusetUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OtusetUserDao {

    private static final String FIND_ALL = "SELECT id, login, password, firstName, lastName, sex, age, interests, city FROM OTUSET_USER";
    private static final String CREATE = "INSERT into OTUSET_USER(login, password, firstName, lastName, sex, age, interests, city) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT id, login, password, firstName, lastName, sex, age, interests, city FROM OTUSET_USER " +
            "WHERE id = ?";
    private static final String FIND_BY_LOGIN = "SELECT id, login, password, firstName, lastName, sex, age, interests, city FROM OTUSET_USER " +
            "WHERE login = ?";
    private static final String FIND_BY_FIO = "SELECT id, login, firstName, lastName, sex, age, interests, city FROM OTUSET_USER " +
            "WHERE lastName like ? AND firstName like ?";

    private final Connection masterConnection;
    private final Connection slaveConnection;

    private OtusetUser buildFromResultSet(ResultSet rs) throws SQLException {
        return OtusetUser.builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
                .sex(rs.getString("sex"))
                .age(rs.getInt("age"))
                .interests(rs.getString("interests"))
                .city(rs.getString("city"))
                .build();
    }

    public List<OtusetUser> getAllUsers() throws SQLException {
        try (Statement stmt = slaveConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(FIND_ALL);
            List<OtusetUser> result = new ArrayList<>();
            while (rs.next()) {
                OtusetUser user = buildFromResultSet(rs);
                result.add(user);
            }
            return result;
        } catch (SQLException ex) {
            log.error("", ex);
            throw ex;
        }
    }

    public long createUser(OtusetUser newUser) throws SQLException {
        try (PreparedStatement stmt = masterConnection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, newUser.getLogin());
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.getFirstName());
            stmt.setString(4, newUser.getLastName());
            stmt.setString(5, newUser.getSex());
            stmt.setInt(6, newUser.getAge());
            stmt.setString(7, newUser.getInterests());
            stmt.setString(8, newUser.getCity());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.first();
            return rs.getLong(1);
        } catch (SQLException ex) {
            log.error("", ex);
            throw ex;
        }
    }

    public Optional<OtusetUser> findById(Long id) throws SQLException {
        try (PreparedStatement stmt = slaveConnection.prepareStatement(FIND_BY_ID)) {
            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(buildFromResultSet(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            log.error("", ex);
            throw ex;
        }
    }

    public Optional<OtusetUser> findByLogin(String login) throws SQLException {
        try (PreparedStatement stmt = slaveConnection.prepareStatement(FIND_BY_LOGIN)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(buildFromResultSet(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            log.error("", ex);
            throw ex;
        }
    }

    public List<OtusetUser> findByFIO(String firstName, String lastName) throws SQLException {
        try (PreparedStatement stmt = slaveConnection.prepareStatement(FIND_BY_FIO)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            ResultSet rs = stmt.executeQuery();
            List<OtusetUser> result = new LinkedList<>();
            while (rs.next()) {
                result.add(OtusetUser.builder()
                        .id(rs.getLong(1))
                        .login(rs.getString(2))
                        .firstName(rs.getString(3))
                        .lastName(rs.getString(4))
                        .sex(rs.getString(5))
                        .age(rs.getInt(6))
                        .interests(rs.getString(7))
                        .city(rs.getString(8))
                        .build());
            }
            return result;
        } catch (SQLException ex) {
            log.error("", ex);
            throw ex;
        }
    }
}

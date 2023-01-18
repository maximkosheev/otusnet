package ru.otusstudy.otuset.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.otusstudy.otuset.domain.OtusetUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OtusetUserDao {

    private static final String FIND_ALL = "SELECT id, firstName, lastName, sex, age, interests, city FROM OTUSET_USER";
    private static final String CREATE = "INSERT into OTUSET_USER(firstName, lastName, sex, age, interests, city) " +
            "VALUES(?, ?, ?, ?, ?, ?)";

    private final Connection dbConnection;

    public List<OtusetUser> getAllUsers() throws SQLException {
        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery(FIND_ALL);
        List<OtusetUser> result = new ArrayList<>();
        while (rs.next()) {
            OtusetUser user = OtusetUser.builder()
                    .id(rs.getLong("id"))
                    .firstName(rs.getString("firstName"))
                    .lastName(rs.getString("lastName"))
                    .sex(rs.getString("sex"))
                    .age(rs.getInt("age"))
                    .interests(rs.getString("interests"))
                    .city(rs.getString("city"))
                    .build();
            result.add(user);
        }
        return result;
    }

    public long createUser(OtusetUser newUser) throws SQLException {
        PreparedStatement stmt = dbConnection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, newUser.getFirstName());
        stmt.setString(2, newUser.getLastName());
        stmt.setString(3, newUser.getSex());
        stmt.setInt(4, newUser.getAge());
        stmt.setString(5, newUser.getInterests());
        stmt.setString(6, newUser.getCity());
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getLong(1);
    }
}

package ru.otusstudy.otuset.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.otusstudy.otuset.domain.OtusetUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OtusetUserDao {

    private static final String FIND_ALL = "SELECT id, firstName, lastName, sex, age, interests, city FROM OTUSET_USER";

    private final Connection dbConnection;

    public List<OtusetUser> getAllUsers() {
        try {
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
        } catch (SQLException ex) {
            log.error("Error occurred while getting user list:", ex);
            return null;
        }
    }
}

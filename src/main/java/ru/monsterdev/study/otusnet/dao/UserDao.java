package ru.monsterdev.study.otusnet.dao;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.monsterdev.study.otusnet.enums.Sex;
import ru.monsterdev.study.otusnet.models.SocialUser;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDao {

  private static final String FIND_BY_ID_SQL = "SELECT * FROM SocialUser WHERE id = ?";
  private static final String REGISTER_SQL = "INSERT INTO SocialUser(login, password, firstName, lastName, age, sex, interests, city) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

  private final JdbcTemplate jdbcTemplate;

  public SocialUser registerUser(SocialUser socialUser) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement stm = con.prepareStatement(REGISTER_SQL, new String[] {"id"});
      stm.setString(1, socialUser.getLogin());
      stm.setString(2, socialUser.getPassword());
      stm.setString(3, socialUser.getFirstName());
      stm.setString(4, socialUser.getLastName());
      stm.setInt(5, socialUser.getAge());
      stm.setString(6, socialUser.getSex().name());
      stm.setString(7, String.join(",", socialUser.getInterests()));
      stm.setString(8, socialUser.getCity());
      return stm;
    }, keyHolder);
    Objects.requireNonNull(keyHolder.getKey());
    socialUser.setId(keyHolder.getKey().longValue());
    return socialUser;
  }

  public Optional<SocialUser> findUserById(long id) {
    List<SocialUser> users = jdbcTemplate.query(FIND_BY_ID_SQL,
      ps -> ps.setLong(1, id),
      (rs, rowNum) -> {
        SocialUser entity = new SocialUser();
        entity.setId(rs.getLong("id"));
        entity.setLogin(rs.getString("login"));
        entity.setPassword(rs.getString("password"));
        entity.setFirstName(rs.getString("firstName"));
        entity.setLastName(rs.getString("lastName"));
        entity.setAge(rs.getInt("age"));
        entity.setSex(Sex.valueOf(rs.getString("sex")));
        entity.setInterests(Arrays.asList(rs.getString("interests").split(",")));
        entity.setCity(rs.getString("city"));
        return entity;
      });
    if (users.size() > 1) {
      log.error("Найдено более одного пользователя с идентификатором {}", id);
      throw new DataIntegrityViolationException("Найдено более одного пользователя");
    }
    return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
  }
}

package ru.monsterdev.study.otusnet.dao;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.monsterdev.study.otusnet.enums.Sex;
import ru.monsterdev.study.otusnet.models.SocialUser;

public class UserDaoTest {

  private JdbcTemplate jdbcTemplate;
  private UserDao userDao;


  @BeforeEach
  void setup() {
    EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.H2)
      .addScript("sql/createSocialUserTable.sql")
      .build();
    jdbcTemplate = new JdbcTemplate(db);
    userDao = new UserDao(jdbcTemplate);
  }

  @Test
  void registerUser_shouldSaveUser() {
    SocialUser socialUser = new SocialUser();
    socialUser.setLogin("login");
    socialUser.setPassword("password");
    socialUser.setFirstName("firstName");
    socialUser.setLastName("lastName");
    socialUser.setAge(10);
    socialUser.setSex(Sex.MALE);
    socialUser.setInterests(Arrays.asList("Bike", "Ski"));
    socialUser.setCity("NY");
    SocialUser dbSocialUser = userDao.registerUser(socialUser);
    Assertions.assertThat(dbSocialUser.getId()).isNotNull();

    List<SocialUser> foundedUsers = jdbcTemplate.query("SELECT * from SocialUser WHERE id = ?",
      ps -> ps.setLong(1, dbSocialUser.getId()),
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
    Assertions.assertThat(foundedUsers).hasSize(1);
    Assertions.assertThat(foundedUsers.get(0)).isNotNull();
    Assertions.assertThat(foundedUsers.get(0).getId()).isEqualTo(dbSocialUser.getId());
    Assertions.assertThat(foundedUsers.get(0).getLogin()).isEqualTo(socialUser.getLogin());
    Assertions.assertThat(foundedUsers.get(0).getPassword()).isNotBlank();
    Assertions.assertThat(foundedUsers.get(0).getFirstName()).isEqualTo(socialUser.getFirstName());
    Assertions.assertThat(foundedUsers.get(0).getLastName()).isEqualTo(socialUser.getLastName());
    Assertions.assertThat(foundedUsers.get(0).getAge()).isEqualTo(socialUser.getAge());
    Assertions.assertThat(foundedUsers.get(0).getSex()).isEqualTo(socialUser.getSex());
    Assertions.assertThat(foundedUsers.get(0).getInterests()).containsExactlyInAnyOrder("Bike", "Ski");
    Assertions.assertThat(foundedUsers.get(0).getCity()).isEqualTo(socialUser.getCity());
  }
}

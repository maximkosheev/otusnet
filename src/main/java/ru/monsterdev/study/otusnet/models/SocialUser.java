package ru.monsterdev.study.otusnet.models;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import ru.monsterdev.study.otusnet.enums.Sex;

@Data
@ToString(exclude = "password")
public class SocialUser {
  private Long id;
  private String login;
  private String password;
  private String firstName;
  private String lastName;
  private Integer age;
  private Sex sex;
  private List<String> interests;
  private String city;
}

package ru.monsterdev.study.otusnet.models;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import ru.monsterdev.study.otusnet.enums.Sex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString(exclude = "password")
public class SocialUser {
  private Long id;
  @NotBlank(message = "Логин не может быть пустым")
  private String login;
  @NotBlank(message = "пароль не может быть пустым")
  @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
  private String password;
  private String firstName;
  private String lastName;
  private Integer age;
  private Sex sex;
  private List<String> interests;
  private String city;
}

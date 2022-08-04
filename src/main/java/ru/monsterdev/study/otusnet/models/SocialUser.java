package ru.monsterdev.study.otusnet.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.monsterdev.study.otusnet.enums.Sex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString(exclude = "password")
public class SocialUser implements UserDetails {
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

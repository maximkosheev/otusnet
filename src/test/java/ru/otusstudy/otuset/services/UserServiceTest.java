package ru.otusstudy.otuset.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otusstudy.otuset.PasswordEncoderConfig;
import ru.otusstudy.otuset.config.SecurityConfig;
import ru.otusstudy.otuset.dao.OtusetUserDao;
import ru.otusstudy.otuset.domain.OtusetUser;
import ru.otusstudy.otuset.exeptions.ServiceException;
import ru.otusstudy.otuset.mappers.UserMapperImpl;
import ru.otusstudy.otuset.models.dto.requests.CreateUserDto;
import ru.otusstudy.otuset.models.dto.responses.UserDto;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        UserService.class,
        UserMapperImpl.class
})
@Import(PasswordEncoderConfig.class)
class UserServiceTest {

    @MockBean
    private OtusetUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    void getAll_shouldReturnList() throws Exception {
        when(userDao.getAllUsers())
                .thenReturn(Arrays.asList(
                        OtusetUser.builder()
                                .id(1L)
                                .firstName("firstName1")
                                .lastName("lastName1")
                                .sex("M")
                                .age(20)
                                .interests("Reading, Running")
                                .city("London")
                                .build(),
                        OtusetUser.builder()
                                .id(2L)
                                .firstName("firstName2")
                                .lastName("lastName2")
                                .sex("F")
                                .age(20)
                                .interests("Money, Cars")
                                .city("NY")
                                .build()
                ));
        List<UserDto> users = userService.getAll();
        Assertions.assertThat(users).containsExactlyInAnyOrder(
                UserDto.builder()
                        .id(1L)
                        .firstName("firstName1")
                        .lastName("lastName1")
                        .sex("M")
                        .age(20)
                        .interests(Arrays.asList("Reading", "Running"))
                        .city("London")
                        .build(),
                UserDto.builder()
                        .id(2L)
                        .firstName("firstName2")
                        .lastName("lastName2")
                        .sex("F")
                        .age(20)
                        .interests(Arrays.asList("Money", "Cars"))
                        .city("NY")
                        .build());
    }

    @Test
    void getAll_shouldReturnEmpty() throws Exception {
        doThrow(SQLException.class).when(userDao).getAllUsers();
        Assertions.assertThatThrownBy(() -> userService.getAll())
                .isInstanceOf(ServiceException.class);
    }

    @Test
    void createUser_shouldCallCreateUser() throws Exception {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .login("login")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .sex("M")
                .age(20)
                .interests(Arrays.asList("Running", "Swimming"))
                .city("City")
                .build();

        when(userDao.createUser(any())).thenReturn(1L);
        ArgumentCaptor<OtusetUser> userArgCapture = ArgumentCaptor.forClass(OtusetUser.class);
        Assertions.assertThat(userService.createUser(createUserDto)).isEqualTo(1L);
        verify(userDao).createUser(userArgCapture.capture());
        Assertions.assertThat(userArgCapture.getValue().getLogin()).isEqualTo(createUserDto.getLogin());
        Assertions.assertThat(passwordEncoder.matches(createUserDto.getPassword(),
                userArgCapture.getValue().getPassword())).isTrue();
        Assertions.assertThat(userArgCapture.getValue().getFirstName()).isEqualTo(createUserDto.getFirstName());
        Assertions.assertThat(userArgCapture.getValue().getLastName()).isEqualTo(createUserDto.getLastName());
        Assertions.assertThat(userArgCapture.getValue().getSex()).isEqualTo(createUserDto.getSex());
        Assertions.assertThat(userArgCapture.getValue().getAge()).isEqualTo(createUserDto.getAge());
        Assertions.assertThat(userArgCapture.getValue().getInterests()).isEqualTo("Running,Swimming");
        Assertions.assertThat(userArgCapture.getValue().getCity()).isEqualTo(createUserDto.getCity());
    }

    @Test
    void createUser_shouldThrowError() throws Exception {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .login("login")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .sex("M")
                .age(20)
                .interests(Arrays.asList("Running", "Swimming"))
                .city("City")
                .build();

        doThrow(SQLException.class).when(userDao).createUser(any());
        Assertions.assertThatThrownBy(() -> userService.createUser(createUserDto))
                .isInstanceOf(ServiceException.class);
    }

}

package ru.otusstudy.otuset.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otusstudy.otuset.dao.OtusetUserDao;
import ru.otusstudy.otuset.domain.OtusetUser;
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
class UserServiceTest {
    @MockBean
    private OtusetUserDao userDao;

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
        List<UserDto> users = userService.getAll().get();
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
        Assertions.assertThat(userService.getAll()).isEmpty();
    }

    @Test
    void createUser_shouldCallCreateUser() throws Exception {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("firstName")
                .lastName("lastName")
                .sex("M")
                .age(20)
                .interests(Arrays.asList("Running", "Swimming"))
                .city("City")
                .build();

        when(userDao.createUser(any())).thenReturn(1L);
        ArgumentCaptor<OtusetUser> userArgCapture = ArgumentCaptor.forClass(OtusetUser.class);
        Assertions.assertThat(userService.createUser(createUserDto)).contains(1L);
        verify(userDao).createUser(userArgCapture.capture());
        Assertions.assertThat(userArgCapture.getValue().getFirstName()).isEqualTo(createUserDto.getFirstName());
        Assertions.assertThat(userArgCapture.getValue().getLastName()).isEqualTo(createUserDto.getLastName());
        Assertions.assertThat(userArgCapture.getValue().getSex()).isEqualTo(createUserDto.getSex());
        Assertions.assertThat(userArgCapture.getValue().getAge()).isEqualTo(createUserDto.getAge());
        Assertions.assertThat(userArgCapture.getValue().getInterests()).isEqualTo("Running,Swimming");
        Assertions.assertThat(userArgCapture.getValue().getCity()).isEqualTo(createUserDto.getCity());
    }

    @Test
    void createUser_shouldReturnEmpty() throws Exception {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("firstName")
                .lastName("lastName")
                .sex("M")
                .age(20)
                .interests(Arrays.asList("Running", "Swimming"))
                .city("City")
                .build();

        doThrow(SQLException.class).when(userDao).createUser(any());
        Assertions.assertThat(userService.createUser(createUserDto)).isEmpty();
    }

}

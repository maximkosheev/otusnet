package ru.otusstudy.otuset.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otusstudy.otuset.domain.OtusetUser;

import java.util.List;

@ExtendWith(DBUnitExtension.class)
@SpringBootTest(classes = {OtusetUserDao.class})
@Import({DBTestConfig.class})
class OtusetUserDaoTest {
    @Autowired
    private OtusetUserDao userDao;

    private static Flyway flyway;

    @BeforeAll
    public static void initDB(){
        flyway = new Flyway(Flyway.configure()
                .dataSource("jdbc:h2:mem:default;MODE=LEGACY;DB_CLOSE_DELAY=-1", "sa", "")
                .locations("filesystem:src/test/resources/migration"));
        flyway.migrate();
    }

    @Test
    @DataSet(value = "dataset.yml", executorId = "flyway")
    void getAllUsers_shouldReturnAllUsers() throws Exception {
        List<OtusetUser> users = userDao.getAllUsers();
        System.out.println(users.size());
    }
}

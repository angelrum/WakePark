package ru.project.wakepark.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.project.wakepark.UserTestData;
import ru.project.wakepark.model.User;
import ru.project.wakepark.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.project.wakepark.CompanyTestData.NOT_FOUND_ID;
import static ru.project.wakepark.CompanyTestData.WAKE_ID1;
import static ru.project.wakepark.UserTestData.*;

@SpringBootTest
@Sql(scripts = "classpath:db/data-test.sql", config = @SqlConfig(encoding = "UTF-8"))
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    protected UserService service;

    @Test
    void getAll() {
        USER_MATCHER.assertMatch(service.getAll(WAKE_ID1), List.of(USER1, USER2, USER3));
    }

    @Test
    void get() {
        USER_MATCHER.assertMatch(service.get(USER_ID1, WAKE_ID1), USER1);
    }

    @Test
    void create() {
        User userNew = UserTestData.getNew();
        User userCr = (User) service.create(userNew, WAKE_ID1, USER_ID1);
        userNew.setId(userCr.getId());
        USER_MATCHER.assertMatch(service.get(userNew.id(), WAKE_ID1), userNew);
    }

    @Test
    void delete() {
        USER_MATCHER.assertMatch(service.getAll(WAKE_ID1), List.of(USER1, USER2, USER3));
        service.delete(USER_ID2, WAKE_ID1);
        USER_MATCHER.assertMatch(service.getAll(WAKE_ID1), List.of(USER1, USER3));
        assertThatThrownBy(()->service.get(USER_ID2, WAKE_ID1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void update() {
        User userUpd = getUpdate();
        service.update(userUpd, WAKE_ID1, USER_ID1);
        USER_MATCHER.assertMatch(userUpd, service.get(userUpd.id(), WAKE_ID1));
    }

    @Test
    void getNotFound() {
        assertThatThrownBy(()->service.get(NOT_FOUND_ID, WAKE_ID1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateNotFound() {
        User userUpd = getUpdate();
        userUpd.setId(NOT_FOUND_ID);

        assertThatThrownBy(()->service.update(userUpd, WAKE_ID1, USER_ID1)).isInstanceOf(NotFoundException.class);
    }

}
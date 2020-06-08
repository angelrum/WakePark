package ru.project.wakepark.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.project.wakepark.TimingExtension;
import ru.project.wakepark.testdata.TestData;
import ru.project.wakepark.TestMatcher;
import ru.project.wakepark.model.AbstractDateChangedEntity;
import ru.project.wakepark.util.exception.NotFoundException;

import static ru.project.wakepark.testdata.CompanyTestData.*;
import static ru.project.wakepark.testdata.UserTestData.USER_ID2;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Sql(scripts = "classpath:db/data-test.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("test")
@ExtendWith(TimingExtension.class)
abstract class AbstractServiceTest <S extends AbstractDateChangedEntity> {

    private AbstractService<S> service;

    private TestMatcher<S> matcher;

    private TestData<S> testData;

    AbstractServiceTest(AbstractService<S> service, TestMatcher<S> matcher, TestData<S> testData) {
        this.service = service;
        this.matcher = matcher;
        this.testData = testData;
    }

    @Test
    void getAll() {
        matcher.assertMatch(service.getAll(WAKE_ID1), testData.getAll());
    }

    @Test
    void get() {
        matcher.assertMatch(service.get(testData.getId(), WAKE_ID1), testData.getOne());
    }

    @Test
    void create() {
        S exNew = testData.getNew();
        S exCr = service.create(exNew, WAKE_ID1, testData.getCreatedId());
        exNew.setId(exCr.id());
        matcher.assertMatch(service.get(exNew.id(), WAKE_ID1), exNew);
    }

    @Test
    void delete() {
        matcher.assertMatch(service.getAll(WAKE_ID1), testData.getAll());
        service.delete(testData.getIdDelete(), WAKE_ID1);
        matcher.assertMatch(service.getAll(WAKE_ID1), testData.getAllWithoutDelete());
        assertThatThrownBy(()->service.get(USER_ID2, WAKE_ID1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void update() {
        S exUpd = testData.getUpdate();
        service.update(exUpd, WAKE_ID1, testData.getCreatedId());
        matcher.assertMatch(exUpd, service.get(exUpd.id(), WAKE_ID1));
    }

    @Test
    void getNotFound() {
        assertThatThrownBy(()->service.get(NOT_FOUND_ID, WAKE_ID1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateNotFound() {
        S exUpd = testData.getUpdate();
        exUpd.setId(NOT_FOUND_ID);

        assertThatThrownBy(()->service.update(exUpd, WAKE_ID1, testData.getCreatedId())).isInstanceOf(NotFoundException.class);
    }

}

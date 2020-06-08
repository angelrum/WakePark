package ru.project.wakepark.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.project.wakepark.model.Company;
import ru.project.wakepark.util.exception.NotFoundException;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.project.wakepark.testdata.CompanyTestData.*;

@SpringBootTest
@Sql(scripts = "classpath:db/data-test.sql", config = @SqlConfig(encoding = "UTF-8"))
class CompanyServiceTest {

    @Autowired
    protected CompanyService service;

    @Test
    void get() {
        COMPANY_MATCHER.assertMatch(service.get(WAKE_ID1), WAKE_PARK1);
    }

    @Test
    void getNotFound() {
        Assertions.assertThrows(NotFoundException.class, ()->service.get(NOT_FOUND_ID));
    }

    @Test
    void delete() {
        service.delete(WAKE_ID1);
        assertThatThrownBy(()->service.get(WAKE_ID1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void create() {
        Company newCompany = getNew();
        Company crCompany = service.create(newCompany);
        newCompany.setId(crCompany.getId());
        COMPANY_MATCHER.assertMatch(crCompany, newCompany);
        COMPANY_MATCHER.assertMatch(service.get(newCompany.getId()), newCompany);
    }

    @Test
    void update() {
        Company updCompany = getUpdate();
        service.update(updCompany);
        COMPANY_MATCHER.assertMatch(service.get(updCompany.getId()), updCompany);
    }

    @Test
    void updateException() {
        Company updCompany = getUpdate();
        updCompany.setId(NOT_FOUND_ID);
        Assertions.assertThrows(NotFoundException.class, ()->service.update(updCompany));
    }

    @Test
    void getAll() {
        COMPANY_MATCHER.assertMatch(service.getAll(), COMPANYS);
    }
}
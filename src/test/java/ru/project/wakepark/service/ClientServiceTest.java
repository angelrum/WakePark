package ru.project.wakepark.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.project.wakepark.model.Client;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.project.wakepark.testdata.ClientTestData.*;

import ru.project.wakepark.testdata.ClientTestData;
import ru.project.wakepark.testdata.CompanyTestData;
import ru.project.wakepark.testdata.UserTestData;
import ru.project.wakepark.to.BaseTo;
import ru.project.wakepark.to.ClientTo;
import ru.project.wakepark.util.exception.NotFoundException;

@SpringBootTest
public class ClientServiceTest extends AbstractServiceTest<Client> {

    private ClientService service;

    @Autowired
    public ClientServiceTest(ClientService service) {
        super(service, CLIENT_MATCHER, new ClientTestData());
        this.service = service;
    }

    @Test
    void getByPhone() {
        Client client = service.getByPhone(CLIENT1.getTelnumber(), CompanyTestData.WAKE_ID1);
        CLIENT_MATCHER.assertMatch(client, CLIENT1);
    }

    @Test
    void getByPhoneNotFound() {
        assertThatThrownBy(()->service.getByPhone(NOT_FOUND_PHONE, CompanyTestData.WAKE_ID1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void dublicatePhone() {
        Client cl = getDublicatePhone();
        assertThatThrownBy(()->service.create(cl, CompanyTestData.WAKE_ID1, UserTestData.USER_ID1)).isInstanceOf(DataIntegrityViolationException.class);
    }
}

package ru.project.wakepark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.project.wakepark.testdata.UserTestData;
import ru.project.wakepark.model.User;
import ru.project.wakepark.to.BaseTo;

import static ru.project.wakepark.testdata.UserTestData.*;

@SpringBootTest
class UserServiceTest extends AbstractServiceTest<User> {

    private UserService service;

    @Autowired
    public UserServiceTest(UserService service) {
        super(service, USER_MATCHER, new UserTestData());
        this.service = service;
    }
}
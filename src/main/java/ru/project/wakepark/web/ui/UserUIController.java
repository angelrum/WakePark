package ru.project.wakepark.web.ui;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.project.wakepark.model.User;
import ru.project.wakepark.service.UserService;
import ru.project.wakepark.to.UserTo;
import ru.project.wakepark.util.UserUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/ajax/controller/users")
public class UserUIController extends AbstractUIController<User, UserTo> {

    public UserUIController(UserService service) {
        super(service, UserUtil.getInstance());
    }
}

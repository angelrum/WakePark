package ru.project.wakepark.util;

import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.Role;
import ru.project.wakepark.model.User;
import ru.project.wakepark.to.UserTo;

import java.util.Objects;

public class UserUtil extends AbstractUtil<User, UserTo> {

    private static UserUtil instance;

    public static UserUtil getInstance() {
        if (Objects.isNull(instance))
            instance = new UserUtil();
        return instance;
    }

    @Override
    public UserTo createTo(User user) {
        Role role = Role.USER;
        if (!CollectionUtils.isEmpty(user.getRoles()))
            role = user.getRoles().iterator().next();
        return new UserTo(user.getId(), user.getAvatar(), user.getFirstname(), user.getLastname(), user.getMiddlename(), user.getTelnumber(), user.getEmail(), user.getLogin(), user.getPassword(), user.isEnabled(), role);
    }
}

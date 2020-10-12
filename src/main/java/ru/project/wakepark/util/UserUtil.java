package ru.project.wakepark.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
        return new UserTo(user.getCompanyId(), user.getId(), user.getAvatar(), user.getFirstname(), user.getLastname(), user.getMiddlename(), user.getTelnumber(), user.getEmail(), user.getLogin(), user.getPassword(), user.isEnabled(), role);
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        return user;
    }
}

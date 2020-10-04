package ru.project.wakepark.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.project.wakepark.model.Role;

import java.beans.ConstructorProperties;

@EqualsAndHashCode(callSuper = true)
@Getter @Setter
public class UserTo extends PersonTo {

    private String avatar;

    private final String login;

    private final String password;

    private final Boolean enabled;

    private final Role roles;

    @ConstructorProperties({"id", "avatar", "firstname", "lastname", "middlename", "telnumber", "email", "login", "password", "enabled", "role"})
    public UserTo(Integer id, String avatar, String firstname, String lastname, String middlename, String telnumber, String email, String login, String password, Boolean enabled, Role role) {
        super(id, firstname, lastname, middlename, telnumber, email);
        this.avatar = avatar;
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.roles = role;
    }
}

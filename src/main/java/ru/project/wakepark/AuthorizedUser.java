package ru.project.wakepark;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.project.wakepark.model.Role;
import ru.project.wakepark.model.User;
import ru.project.wakepark.to.UserTo;
import ru.project.wakepark.util.UserUtil;

import java.util.HashSet;
import java.util.Set;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;
    private static final String ROLE_PREFIX = "ROLE_";

    public static final int DEF_COMPANY = 10_000;

    public static final int DEF_USER = 10_001;

    @Getter
    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getLogin(), user.getPassword(), user.isEnabled(), true, true, true, getAuthority(user.getRoles()));
        this.userTo = UserUtil.getInstance().createTo(user);
    }

    private static Set<GrantedAuthority> getAuthority(Set<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.name())));
        return authorities;
    }
}

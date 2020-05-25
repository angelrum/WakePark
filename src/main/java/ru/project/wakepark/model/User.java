package ru.project.wakepark.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User extends AbstractDateChangedEntity {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    private boolean enabled = true;

    public User() {
    }

    public User(Integer id, Integer companyId, @NotBlank String firstname, @NotBlank String lastname, @NotBlank String middlename, @NotBlank String telnumber, @NotNull LocalDateTime createdOn, Integer createdBy, LocalDateTime changedOn, Integer changedBy, @NotBlank String login, @NotBlank String password, @Email @NotBlank String email, boolean enabled) {
        super(id, companyId, firstname, lastname, middlename, telnumber, createdOn, createdBy, changedOn, changedBy);
        this.login = login;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
    }

    public User(User u) {
        super(u.id, u.companyId, u.firstname, u.lastname, u.middlename, u.telnumber, u.createdOn, u.createdBy, u.changedOn, u.changedBy);
        this.login = u.login;
        this.password = u.password;
        this.email = u.email;
        this.enabled = u.enabled;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}

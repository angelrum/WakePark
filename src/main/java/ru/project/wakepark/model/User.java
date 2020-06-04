package ru.project.wakepark.model;

import org.hibernate.annotations.BatchSize;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity implements Serializable {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    private Set<Role> roles;

    public User(Integer id, @NotNull Integer companyId,
                @NotBlank String login, @NotBlank String password,
                @NotBlank String firstname, @NotBlank String lastname, @NotBlank String middlename,
                @NotBlank String telnumber, @Email @NotBlank String email, boolean enabled,
                @NotNull LocalDateTime createdOn, User createdBy,
                LocalDateTime changedOn, User changedBy, Role role, Role...roles) {
        this(id, companyId, login, password, firstname, lastname, middlename, telnumber, email, enabled, createdOn, createdBy, changedOn, changedBy, EnumSet.of(role, roles));
    }

    public User(Integer id, @NotNull Integer companyId,
                @NotBlank String login, @NotBlank String password,
                @NotBlank String firstname, @NotBlank String lastname, @NotBlank String middlename,
                @NotBlank String telnumber, @Email @NotBlank String email, boolean enabled,
                @NotNull LocalDateTime createdOn, User createdBy,
                LocalDateTime changedOn, User changedBy, Collection<Role> roles) {
        super(id, companyId, createdOn, createdBy, changedOn, changedBy, firstname, lastname, middlename, telnumber);
        this.login = login;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        setRoles(roles);
    }

    public User(User u) {
        this(u.id, u.companyId, u.login, u.password, u.firstname, u.lastname, u.middlename, u.telnumber, u.email, u.enabled, u.createdOn, u.createdBy, u.changedOn, u.changedBy, u.roles);
    }

    public User() {

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

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", telnumber='" + telnumber + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", createdOn=" + createdOn +
                ", createdBy=" + (Objects.isNull(createdBy) ? null : createdBy.getLogin()) +
                ", changedOn=" + changedOn +
                ", changedBy=" + (Objects.isNull(changedBy) ? null : changedBy.getLogin()) +
                '}';
    }
}

package ru.project.wakepark.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractDateChangedEntity {

    @NotBlank
    protected String firstname;

    @NotBlank
    protected String lastname;

    protected String middlename;

    @NotBlank
    protected String telnumber;

    @Email
    protected String email;

    public AbstractNamedEntity(Integer id, Integer companyId,
                               @NotBlank String firstname, @NotBlank String lastname, @NotBlank String middlename, @NotBlank String telnumber,
                               String email, @NotNull LocalDateTime createdOn, User createdBy, LocalDateTime changedOn, User changedBy) {
        super(id, companyId, createdOn, createdBy, changedOn, changedBy);
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.telnumber = telnumber;
        this.email = email;
    }

    public AbstractNamedEntity() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

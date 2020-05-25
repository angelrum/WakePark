package ru.project.wakepark.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    protected String firstname;

    @NotBlank
    protected String lastname;

    @NotBlank
    protected String middlename;

    @NotBlank
    protected String telnumber;

    public AbstractNamedEntity(Integer id, Integer companyId, @NotBlank String firstname, @NotBlank String lastname, @NotBlank String middlename, @NotBlank String telnumber) {
        super(id, companyId);
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.telnumber = telnumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractNamedEntity that = (AbstractNamedEntity) o;
        return firstname.equals(that.firstname) &&
                lastname.equals(that.lastname) &&
                middlename.equals(that.middlename) &&
                telnumber.equals(that.telnumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstname, lastname, middlename, telnumber);
    }

    @Override
    public String toString() {
        return super.toString() +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", telnumber='" + telnumber;
    }
}

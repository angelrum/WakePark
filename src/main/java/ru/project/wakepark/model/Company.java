package ru.project.wakepark.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "companys")
public class Company extends AbstractKeyEntity  {

    @NotBlank
    protected String name;

    @NotBlank
    protected String contactName;

    @NotBlank
    protected String contactPhone;

    public Company(Integer id, @NotBlank String name, @NotBlank String contactName, @NotBlank String contactPhone) {
        super(id);
        this.name = name;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public Company(Company company) {
        this(company.getId(), company.getName(), company.getContactName(), company.getContactPhone());
    }

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", id=" + id +
                '}';
    }

}

package ru.project.wakepark.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client extends AbstractNamedEntity{

    protected String city;

    public Client(Integer id, Integer companyId,
                  @NotBlank String firstname, @NotBlank String lastname, @NotBlank String middlename,
                  @NotBlank String telnumber, String email, String city,
                  @NotNull LocalDateTime createdOn, User createdBy, LocalDateTime changedOn, User changedBy) {
        super(id, companyId, firstname, lastname, middlename, telnumber, email, createdOn, createdBy, changedOn, changedBy);
        this.city = city;
    }

    public Client(Client c) {
        super(c.id, c.companyId, c.firstname, c.lastname, c.middlename, c.telnumber, c.email, c.createdOn, c.createdBy, c.changedOn, c.changedBy);
    }

    public Client() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(city, client.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", telnumber='" + telnumber + '\'' +
                ", changedOn=" + changedOn +
                ", changedBy=" + (Objects.isNull(changedBy) ? null : changedBy.getLogin()) +
                ", createdOn=" + createdOn +
                ", createdBy=" + (Objects.isNull(createdBy) ? null : createdBy.getLogin()) +
                ", company=" + companyId +
                '}';
    }
}

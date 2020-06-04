package ru.project.wakepark.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client extends AbstractNamedEntity{

    protected String city;

    public Client(Integer id, Integer companyId, @NotNull LocalDateTime createdOn, User createdBy, LocalDateTime changedOn, User changedBy, @NotBlank String firstname, @NotBlank String lastname, @NotBlank String middlename, @NotBlank String telnumber, String city) {
        super(id, companyId, createdOn, createdBy, changedOn, changedBy, firstname, lastname, middlename, telnumber);
        this.city = city;
    }

    public Client() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

package ru.project.wakepark.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client extends AbstractDateEntity{

    protected String city;

    public Client(@NotNull LocalDateTime createdOn, Integer createdBy, String city) {
        super(createdOn, createdBy);
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
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(city, client.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), city);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id + '\'' +
                ", companyId=" + companyId + '\'' +
                ", city='" + city + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", telnumber='" + telnumber + '\'' +
                ", createdOn=" + createdOn + '\'' +
                ", createdBy=" + createdBy +
                '}';
    }
}

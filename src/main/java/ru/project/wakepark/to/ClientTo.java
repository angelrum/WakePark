package ru.project.wakepark.to;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class ClientTo extends BaseTo {

    private final String firstname;

    private final String lastname;

    private final String middlename;

    private final String telnumber;

    private final String city;

    private final String email;

    @ConstructorProperties({"id","firstname", "lastname", "middlename", "telnumber", "city", "email"})
    public ClientTo(Integer id, String firstname, String lastname, String middlename, String telnumber, String city, String email) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.telnumber = telnumber;
        this.city = city;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientTo clientTo = (ClientTo) o;
        return firstname.equals(clientTo.firstname) &&
                lastname.equals(clientTo.lastname) &&
                Objects.equals(middlename, clientTo.middlename) &&
                telnumber.equals(clientTo.telnumber) &&
                Objects.equals(city, clientTo.city) &&
                Objects.equals(email, clientTo.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, middlename, telnumber, city);
    }
}

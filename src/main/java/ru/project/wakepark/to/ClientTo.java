package ru.project.wakepark.to;

import lombok.EqualsAndHashCode;
import java.beans.ConstructorProperties;

@EqualsAndHashCode(callSuper = true)
public class ClientTo extends PersonTo {

    private final String city;

    @ConstructorProperties({"id","firstname", "lastname", "middlename", "telnumber", "city", "email"})
    public ClientTo(Integer id, String firstname, String lastname, String middlename, String telnumber, String city, String email) {
        super(id, firstname, lastname, middlename, telnumber, email);
        this.city = city;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ClientTo personTo = (ClientTo) o;
//        return firstname.equals(personTo.firstname) &&
//                lastname.equals(personTo.lastname) &&
//                Objects.equals(middlename, personTo.middlename) &&
//                telnumber.equals(personTo.telnumber) &&
//                Objects.equals(city, personTo.city) &&
//                Objects.equals(email, personTo.email);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, firstname, lastname, middlename, telnumber, city);
//    }
}

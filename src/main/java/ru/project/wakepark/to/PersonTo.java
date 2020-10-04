package ru.project.wakepark.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class PersonTo extends BaseTo {

    private final String firstname;

    private final String lastname;

    private final String middlename;

    private final String telnumber;

    private final String email;

    public PersonTo(Integer id, String firstname, String lastname, String middlename, String telnumber, String email) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.telnumber = telnumber;
        this.email = email;
    }
}

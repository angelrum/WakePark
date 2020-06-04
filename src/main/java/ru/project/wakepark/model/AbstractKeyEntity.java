package ru.project.wakepark.model;

import ru.project.wakepark.util.HasId;

import javax.persistence.*;

@MappedSuperclass
public class AbstractKeyEntity implements HasId {
    public static final int START_SEQ = 10_000;

    public AbstractKeyEntity(Integer id) {
        this.id = id;
    }

    public AbstractKeyEntity() {
    }

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}

package ru.project.wakepark.testdata;

import ru.project.wakepark.model.AbstractDateChangedEntity;

import java.util.List;

public interface TestData <S extends AbstractDateChangedEntity> {

    List<S> getAll();

    int getIdDelete();

    List<S> getAllWithoutDelete();

    S getOne();

    S getNew();

    S getUpdate();

    int getId();

    int getCreatedId();
}

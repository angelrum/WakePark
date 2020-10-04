package ru.project.wakepark.to;

import lombok.AllArgsConstructor;
import ru.project.wakepark.util.HasId;

public abstract class BaseTo implements HasId {

    protected Integer id;

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


}

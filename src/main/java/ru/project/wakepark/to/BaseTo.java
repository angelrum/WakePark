package ru.project.wakepark.to;

import lombok.Getter;
import lombok.Setter;
import ru.project.wakepark.util.HasId;

@Setter @Getter
public abstract class BaseTo implements HasId {

    protected Integer companyId;

    protected Integer id;

    public BaseTo(Integer companyId, Integer id) {
        this.companyId = companyId;
        this.id = id;
    }

}

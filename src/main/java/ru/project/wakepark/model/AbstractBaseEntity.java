package ru.project.wakepark.model;

import ru.project.wakepark.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity extends AbstractKeyEntity {

    @NotNull(groups = {View.Persist.class})
    @Column(name = "company_id")
    protected Integer companyId;

    public AbstractBaseEntity(Integer id, Integer companyId) {
        super(id);
        this.companyId = companyId;
    }

    public AbstractBaseEntity() {
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}


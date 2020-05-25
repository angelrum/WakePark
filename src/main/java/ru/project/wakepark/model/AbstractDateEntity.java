package ru.project.wakepark.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractDateEntity extends AbstractNamedEntity {

    @Column(name = "created_on")
    @NotNull
    protected LocalDateTime createdOn;

    @Column(name = "created_by")
    protected Integer createdBy;

    public AbstractDateEntity(@NotNull LocalDateTime createdOn, Integer createdBy) {
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public AbstractDateEntity() {
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractDateEntity that = (AbstractDateEntity) o;
        return createdOn.equals(that.createdOn) &&
                Objects.equals(createdBy, that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdOn, createdBy);
    }

    @Override
    public String toString() {
        return "createdOn=" + createdOn +
                ", createdBy=" + createdBy;
    }
}

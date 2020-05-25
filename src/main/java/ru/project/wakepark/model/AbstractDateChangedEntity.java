package ru.project.wakepark.model;

import org.springframework.format.annotation.DateTimeFormat;
import ru.project.wakepark.util.DateTimeUtil;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractDateChangedEntity extends AbstractNamedEntity{

    @Column(name = "created_on")
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    protected LocalDateTime createdOn;

    @Column(name = "created_by")
    protected Integer createdBy;

    @Column(name = "changed_on")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    protected LocalDateTime changedOn;

    @Column(name = "changed_by")
    protected Integer changedBy;

    public AbstractDateChangedEntity(Integer id, Integer companyId, @NotBlank String firstname, @NotBlank String lastname, @NotBlank String middlename, @NotBlank String telnumber, @NotNull LocalDateTime createdOn, Integer createdBy, LocalDateTime changedOn, Integer changedBy) {
        super(id, companyId, firstname, lastname, middlename, telnumber);
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.changedOn = changedOn;
        this.changedBy = changedBy;
    }

    public AbstractDateChangedEntity() {
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

    public LocalDateTime getChangedOn() {
        return changedOn;
    }

    public void setChangedOn(LocalDateTime changedOn) {
        this.changedOn = changedOn;
    }

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractDateChangedEntity that = (AbstractDateChangedEntity) o;
        return createdOn.equals(that.createdOn) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(changedOn, that.changedOn) &&
                Objects.equals(changedBy, that.changedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), createdOn, createdBy, changedOn, changedBy);
    }

    @Override
    public String toString() {
        return "createdOn=" + createdOn +
                ", createdBy=" + createdBy +
                ", changedOn=" + changedOn +
                ", changedBy=" + changedBy;
    }
}

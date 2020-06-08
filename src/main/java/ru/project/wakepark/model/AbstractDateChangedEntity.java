package ru.project.wakepark.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import ru.project.wakepark.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractDateChangedEntity extends AbstractDateEntity{

    @Column(name = "changed_on")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    protected LocalDateTime changedOn;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
//   Позволяет делать выборку по двум полям, но игнорирует запись данных в поле
//   Без параметров insertable = false, updatable = false не позволяет использовать соединение
//    @JoinColumns({
//            @JoinColumn(
//                    name = "changed_by",
//                    referencedColumnName = "id", nullable = true, insertable = false, updatable = false),
//            @JoinColumn(
//                    name = "company_id",
//                    referencedColumnName = "company_id", insertable = false, updatable = false)
//    })
    @JoinColumn(name = "changed_by", referencedColumnName = "id")
    protected User changedBy;

    public AbstractDateChangedEntity(Integer id, @NotNull Integer companyId, @NotNull LocalDateTime createdOn,
                                     User createdBy, LocalDateTime changedOn, User changedBy) {
        super(id, companyId, createdOn, createdBy);
        this.changedOn = changedOn;
        this.changedBy = changedBy;
    }

    public AbstractDateChangedEntity() {
    }

    public LocalDateTime getChangedOn() {
        return changedOn;
    }

    public void setChangedOn(LocalDateTime changedOn) {
        this.changedOn = changedOn;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }
}

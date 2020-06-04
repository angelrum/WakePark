package ru.project.wakepark.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import ru.project.wakepark.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractDateEntity extends AbstractBaseEntity {

    @NotNull
    @Column(name = "created_on")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    protected LocalDateTime createdOn;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
//   Позволяет делать выборку по двум полям, но игнорирует запись данных в поле
//   Без параметров insertable = false, updatable = false не позволяет использовать соединение
//    @JoinColumns({
//            @JoinColumn(
//                    name = "created_by",
//                    referencedColumnName = "id", nullable = true, insertable = false, updatable = false),
//            @JoinColumn(
//                    name = "company_id",
//                    referencedColumnName = "company_id", insertable = false, updatable = false)
//    })
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    protected User createdBy;

    public AbstractDateEntity(Integer id, Integer companyId, @NotNull LocalDateTime createdOn, User createdBy) {
        super(id, companyId);
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}

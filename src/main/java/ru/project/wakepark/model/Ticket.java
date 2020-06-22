package ru.project.wakepark.model;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.project.wakepark.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tickets")
public class Ticket extends AbstractDateChangedEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "pass")
    protected Pass pass;

    @NotBlank
    protected String name;

    @Column(name = "enable", nullable = false, columnDefinition = "bool default true")
    protected boolean enable = true;

    @Column(name = "equipment", nullable = false, columnDefinition = "bool default false")
    protected boolean equipment = false;

    @Column(name = "duration", nullable = false)
    @NotNull
    @Range(min = 1, max = 5000)
    protected Integer duration;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    protected LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    protected LocalDate endDate;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = DateTimeUtil.TIME_PATTERN)
    protected LocalTime startTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = DateTimeUtil.TIME_PATTERN)
    protected LocalTime endTime;

    @NotNull
    @Range(min = 0, max = 12)
    protected Integer month;

    @NotNull
    @Min(0)
    protected Integer day;

    @NotNull
    @Range(min = 2019, max = 2099)
    protected Integer year;

    @NotNull
    @Min(0)
    protected Double cost;

    @NotNull
    @Min(0)
    protected Double weekendcost;

    public Ticket() {
    }

    public Ticket(Integer id, @NotNull Integer companyId, Pass pass, @NotBlank String name, boolean enable, boolean equipment, @NotNull @Range(min = 1, max = 5000) Integer duration,
                  LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, @NotNull @Range(min = 0, max = 12) Integer month, @NotNull @Min(0) Integer day,
                  @NotNull @Range(min = 2019, max = 2099) Integer year, @NotNull @Min(0) Double cost, @NotNull @Min(0) Double weekendcost,
                  @NotNull LocalDateTime createdOn, User createdBy, LocalDateTime changedOn, User changedBy) {
        super(id, companyId, createdOn, createdBy, changedOn, changedBy);
        this.pass = pass;
        this.name = name;
        this.enable = enable;
        this.equipment = equipment;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.month = month;
        this.day = day;
        this.year = year;
        this.cost = cost;
        this.weekendcost = weekendcost;
    }

    public Ticket(Ticket t) {
        this(t.id, t.companyId, t.pass, t.name, t.enable, t.equipment, t.duration, t.startDate, t.endDate, t.startTime, t.endTime, t.month, t.day, t.year, t.cost, t.weekendcost, t.createdOn, t.createdBy, t.changedOn, t.changedBy);
    }

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEquipment() {
        return equipment;
    }

    public void setEquipment(boolean equipment) {
        this.equipment = equipment;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getWeekendcost() {
        return weekendcost;
    }

    public void setWeekendcost(Double weekendcost) {
        this.weekendcost = weekendcost;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", pass=" + pass +
                ", name='" + name + '\'' +
                ", enable=" + enable +
                ", equipment=" + equipment +
                ", duration=" + duration +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", month=" + month +
                ", day=" + day +
                ", year=" + year +
                ", cost=" + cost +
                ", weekendcost=" + weekendcost +
                ", createdOn=" + createdOn +
                //", createdBy=" + (Objects.isNull(createdBy) ? null : createdBy.getLogin()) +
                ", changedOn=" + changedOn +
                //", changedBy=" + (Objects.isNull(changedBy) ? null : changedBy.getLogin()) +
                '}';
    }
}

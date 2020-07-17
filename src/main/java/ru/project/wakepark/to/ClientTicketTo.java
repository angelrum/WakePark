package ru.project.wakepark.to;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.time.LocalTime;
import java.util.Objects;

public class ClientTicketTo extends BaseTo {

    @NotNull
    protected int clientId;

    @NotNull
    protected int ticketId;

    protected String pass;

    protected String name;

    protected boolean equipment;

    protected boolean countEdit;

    protected LocalTime startTime;

    protected LocalTime endTime;

    protected Integer month;

    protected Integer day;

    protected Integer duration;

    @NotNull
    @Min(value = 1)
    private Integer count;

    @ConstructorProperties({"id", "client_id", "ticket_id", "pass", "name", "equipment", "countEdt", "startTime", "endTime", "month", "day", "duration", "count"})
    public ClientTicketTo(Integer id, @NotNull Integer clientId, @NotNull Integer ticketId, String pass, String name,
                          Boolean equipment, Boolean countEdit, LocalTime startTime, LocalTime endTime, Integer month,
                          Integer day, Integer duration, @NotNull @Min(value = 1) Integer count) {
        super(id);
        this.clientId = clientId;
        this.ticketId = ticketId;
        this.pass = pass;
        this.name = name;
        this.equipment = Objects.nonNull(equipment) ? equipment : true;
        this.countEdit = Objects.nonNull(countEdit) ? countEdit : true;
        this.startTime = startTime;
        this.endTime = endTime;
        this.month = month;
        this.day = day;
        this.duration = duration;
        this.count = count;
    }

    public int getClientId() {
        return clientId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Integer getCount() {
        return count;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public boolean isEquipment() {
        return equipment;
    }

    public boolean isCountEdit() {
        return countEdit;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientTicketTo that = (ClientTicketTo) o;
        return clientId == that.clientId &&
                ticketId == that.ticketId &&
                equipment == that.equipment &&
                countEdit == that.countEdit &&
                pass.equals(that.pass) &&
                name.equals(that.name) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(month, that.month) &&
                Objects.equals(day, that.day) &&
                duration.equals(that.duration) &&
                count.equals(that.count);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, pass, name, equipment, startTime, endTime, month, day, duration, count);
    }
}

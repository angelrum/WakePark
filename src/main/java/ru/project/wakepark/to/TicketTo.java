package ru.project.wakepark.to;

import ru.project.wakepark.model.Pass;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class TicketTo extends BaseTo {

    protected String pass;

    protected String name;

    protected boolean enable;

    protected boolean equipment;

    protected boolean countEdit;

    protected Integer duration;

    protected LocalDate startDate;

    protected LocalDate endDate;

    protected LocalTime startTime;

    protected LocalTime endTime;

    protected Integer month;

    protected Integer day;

    protected Integer year;

    protected Double cost;

    protected Double weekendcost;

    @ConstructorProperties({"id", "pass", "name", "enable", "equipment", "countEdit", "duration", "startDate", "endDate", "startTime", "endTime", "month", "day", "year", "cost", "weekendcost",})
    public TicketTo(Integer id, Pass pass, String name, boolean enable, boolean equipment, boolean countEdit, Integer duration, LocalDate startDate, LocalDate endDate,
                    LocalTime startTime, LocalTime endTime, Integer month, Integer day, Integer year, Double cost, Double weekendcost) {
        super(null, id);
        this.pass = pass.toString();
        this.name = name;
        this.enable = enable;
        this.equipment = equipment;
        this.countEdit = countEdit;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketTo ticketTo = (TicketTo) o;
        return enable == ticketTo.enable &&
                equipment == ticketTo.equipment &&
                countEdit == ticketTo.countEdit &&
                pass.equals(ticketTo.pass) &&
                name.equals(ticketTo.name) &&
                duration.equals(ticketTo.duration) &&
                Objects.equals(startDate, ticketTo.startDate) &&
                Objects.equals(endDate, ticketTo.endDate) &&
                Objects.equals(startTime, ticketTo.startTime) &&
                Objects.equals(endTime, ticketTo.endTime) &&
                month.equals(ticketTo.month) &&
                day.equals(ticketTo.day) &&
                year.equals(ticketTo.year) &&
                cost.equals(ticketTo.cost) &&
                weekendcost.equals(ticketTo.weekendcost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pass, name, enable, equipment, duration, startDate, endDate, startTime, endTime, month, day, year, cost, weekendcost);
    }
}

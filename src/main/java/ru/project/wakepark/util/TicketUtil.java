package ru.project.wakepark.util;

import ru.project.wakepark.model.Pass;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.to.TicketTo;
import ru.project.wakepark.util.exception.ValidationInputDataException;

import static ru.project.wakepark.util.exception.ValidationInputDataException.*;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicketUtil extends AbstractUtil<Ticket, TicketTo> implements ValidationInputData<Ticket> {

    private static TicketUtil instance;

    private TicketUtil() {
    }

    public static TicketUtil getInstance() {
        if (Objects.isNull(instance))
            instance = new TicketUtil();
        return instance;
    }

    @Override
    public TicketTo createTo(Ticket t) {
        return new TicketTo(t.id(), t.getPass(), t.getName(), t.isEnable(), t.isEquipment(), countEdit(t.getPass()), t.getDuration(), t.getStartDate(),
                t.getEndDate(), t.getStartTime(), t.getEndTime(), t.getMonth(), t.getDay(),t.getYear(), t.getCost(), t.getWeekendcost());
    }
    
    @Override
    public void checkInputData(Ticket ticket) {
        if ((Objects.nonNull(ticket.getStartDate())
                || Objects.nonNull(ticket.getEndDate()))
                && (ticket.getDay() > 0 || ticket.getMonth() > 0))
            throw new ValidationInputDataException(EXCEPTION_VALID_TICKET_DURATION);
        else if ((Objects.nonNull(ticket.getStartDate()) || Objects.nonNull(ticket.getEndDate()))
                && (Objects.isNull(ticket.getStartDate()) || Objects.isNull(ticket.getEndDate())))
            throw new ValidationInputDataException(EXCEPTION_VALID_TICKET_DURATION_FIX);
    }

    public List<Ticket> getInActiveTime (List<Ticket> tickets, LocalTime time) {
        return tickets.stream()
                .filter(t -> (Objects.isNull(t.getStartTime()) && Objects.isNull(t.getEndTime())) || (t.getStartTime().isBefore(time) && t.getEndTime().isAfter(time)))
                .collect(Collectors.toList());

    }

    public static boolean countEdit(Pass pass) {
        return !pass.equals(Pass.SEASON);
    }

    public List<Ticket> getFiltered(Collection<Ticket> tickets, LocalTime startTime, LocalTime endTime, boolean active, boolean equipment) {
        return tickets.stream().filter(
                t -> (Util.isBetweenHalfOpen(t.getStartTime(), startTime, endTime) || Util.isBetweenHalfOpen(t.getEndTime(), startTime, endTime))
                        && (!active || t.isEnable())
                        && (!equipment || t.isEquipment()))
                .collect(Collectors.toList());
    }

    public boolean getBooleanFromString(String bool) {
        return "on".equals(bool);
    }


    public LocalDate getTicketExpirationDate(LocalDate start, Ticket ticket) {
        if (Objects.nonNull(ticket.getEndDate())) return ticket.getEndDate();
        return start
                .plusMonths(ticket.getMonth())
                .plusDays(ticket.getDay());
    }
}

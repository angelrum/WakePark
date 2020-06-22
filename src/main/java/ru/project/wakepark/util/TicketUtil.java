package ru.project.wakepark.util;

import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.to.TicketTo;

import java.util.Objects;

public class TicketUtil extends AbstractUtil<Ticket, TicketTo> {

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
        return new TicketTo(t.id(), t.getPass(), t.getName(), t.isEnable(), t.isEquipment(), t.getDuration(), t.getStartDate(),
                t.getEndDate(), t.getStartTime(), t.getEndTime(), t.getMonth(), t.getDay(),t.getYear(), t.getCost(), t.getWeekendcost());
    }
}

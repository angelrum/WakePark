package ru.project.wakepark.testdata;

import ru.project.wakepark.TestMatcher;
import ru.project.wakepark.model.Pass;
import ru.project.wakepark.model.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import static ru.project.wakepark.testdata.CompanyTestData.*;
import static ru.project.wakepark.testdata.UserTestData.*;

public class TicketTestData implements TestData<Ticket> {

    public static TestMatcher<Ticket> TICKET_MATCHER = TestMatcher.usingFieldsComparator(Ticket.class, "createdBy", "changedBy");

    public static int TICKET_ID1 = 10_008;
    public static int TICKET_ID2 = 10_009;
    public static int TICKET_ID3 = 10_010;

    public static Ticket TICKET1 = new Ticket(TICKET_ID1, WAKE_ID1, Pass.SEASON, "Сезоный абонемент", true, true, 5,
            LocalDate.of(2020, Month.JUNE, 1), LocalDate.of(2020, Month.OCTOBER, 1), null, null, 0, 0,
            2020, 100.0, 120.0, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(11, 0)), USER3, null, null );

    public static Ticket TICKET2 = new Ticket(TICKET_ID2, WAKE_ID1, Pass.SINGLE, "Разовый абонемент", true, true, 5,
            null, null, LocalTime.of(10, 0), LocalTime.of(18, 0), 0, 0,
            2020, 10.0, 11.0, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(12, 0)), USER3, null, null );

    public static Ticket TICKET3 = new Ticket(TICKET_ID3, WAKE_ID1, Pass.SEASON, "Абонемент на месяц", true, false, 5,
            null, null, LocalTime.of(9, 0), LocalTime.of(18, 0), 1, 0,
            2020, 90.0, 95.0, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(13, 0)), USER3, null, null );

    @Override
    public List<Ticket> getAll() {
        return List.of(TICKET1, TICKET2, TICKET3);
    }

    @Override
    public int getIdDelete() {
        return TICKET_ID2;
    }

    @Override
    public List<Ticket> getAllWithoutDelete() {
        return List.of(TICKET1, TICKET3);
    }

    @Override
    public Ticket getOne() {
        return TICKET1;
    }

    @Override
    public Ticket getNew() {
        Ticket newTicket = new Ticket(TICKET1);
        newTicket.setId(null);
        newTicket.setName("Новый абонемент");
        return newTicket;
    }

    @Override
    public Ticket getUpdate() {
        Ticket upd = new Ticket(TICKET1);
        upd.setName("Обновленный сезонный абонемент");
        return upd;
    }

    @Override
    public int getId() {
        return TICKET_ID1;
    }

    @Override
    public int getCreatedId() {
        return USER_ID3;
    }
}

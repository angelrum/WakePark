package ru.project.wakepark.testdata;

import ru.project.wakepark.TestMatcher;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static ru.project.wakepark.testdata.CompanyTestData.*;
import static ru.project.wakepark.testdata.UserTestData.*;
import static ru.project.wakepark.testdata.ClientTestData.*;
import static ru.project.wakepark.testdata.TicketTestData.*;

public class ClientTicketTestData implements TestData<ClientTicket> {

    public static TestMatcher<ClientTicket> CL_TICKET_MATCHER = TestMatcher.usingClassComparator(ClientTicket.class,
            Map.of(Comparator.comparing(Ticket::getId).thenComparing(Ticket::getCompanyId), Ticket.class,
                    Comparator.comparing(Client::getId).thenComparing(Client::getCompanyId), Client.class),
            "createdBy", "changedBy");

    public static final int CL_TICKET_ID1 = 10_011;

    public static final int CL_TICKET_ID2 = 10_012;

    public static final int CL_TICKET_ID3 = 10_013;

    public static final int CL_TICKET_ID4 = 10_014;

    public static final int CLT_NEWCOUNT_CL2 = 3;

    public static final int CLT_NEWCOUNT_CL1 = 1;

    public static ClientTicket CL_TICKET1 = new ClientTicket(CL_TICKET_ID1, WAKE_ID1, CLIENT1, TICKET2, null, null, true, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(10, 0)), USER1, null, null);

    public static ClientTicket CL_TICKET2 = new ClientTicket(CL_TICKET_ID2, WAKE_ID1, CLIENT1, TICKET2, null, null, true, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(11, 0)), USER1, null, null);

    public static ClientTicket CL_TICKET3 = new ClientTicket(CL_TICKET_ID3, WAKE_ID1, CLIENT2, TICKET1, TICKET1.getStartDate(), TICKET1.getEndDate(), true, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(12, 0)), USER2, null, null);

    public static ClientTicket CL_TICKET4 = new ClientTicket(CL_TICKET_ID4, WAKE_ID1, CLIENT1, TICKET1, null, null, false, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(13, 0)), USER1, null, null);

    @Override
    public List<ClientTicket> getAll() {
        return List.of(CL_TICKET1, CL_TICKET2, CL_TICKET3, CL_TICKET4);
    }

    @Override
    public int getIdDelete() {
        return CL_TICKET_ID2;
    }

    @Override
    public List<ClientTicket> getAllWithoutDelete() {
        return List.of(CL_TICKET1, CL_TICKET3, CL_TICKET4);
    }

    @Override
    public ClientTicket getOne() {
        return CL_TICKET1;
    }

    @Override
    public ClientTicket getNew() {
        ClientTicket clt = new ClientTicket(CL_TICKET1);
        clt.setId(null);
        clt.setClient(CLIENT2);
        clt.setTicket(TICKET1);
        clt.setDateStart(TICKET1.getStartDate());
        clt.setDateEnd(TICKET1.getEndDate());
        return clt;
    }

    @Override
    public ClientTicket getUpdate() {
        ClientTicket clt = new ClientTicket(CL_TICKET1);
        clt.setActive(false);
        clt.setChangedOn(LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(11, 0)));
        clt.setChangedBy(USER1);
        return clt;
    }

    @Override
    public int getId() {
        return CL_TICKET_ID1;
    }

    @Override
    public int getCreatedId() {
        return USER_ID3;
    }
}

package ru.project.wakepark.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.project.wakepark.model.Ticket;;
import ru.project.wakepark.testdata.TicketTestData;
import ru.project.wakepark.to.TicketTo;

import java.time.LocalTime;
import java.util.List;

import static ru.project.wakepark.testdata.TicketTestData.*;
import static ru.project.wakepark.testdata.CompanyTestData.*;

@SpringBootTest
class TicketServiceTest extends AbstractServiceTest<Ticket> {

    private TicketService service;

    @Autowired
    TicketServiceTest(TicketService service) {
        super(service, TICKET_MATCHER, new TicketTestData());
        this.service = service;
    }

    @Test
    void getAllEnable() {
        TICKET_MATCHER.assertMatch(service.getAllEnable(WAKE_ID1), List.of(TICKET1, TICKET2));
    }

    @Test
    void getAllInActiveTime() {
        TICKET_MATCHER.assertMatch(service.getAllEnableInActiveTime(WAKE_ID1, LocalTime.of(9,5)), List.of(TICKET1));
    }

}

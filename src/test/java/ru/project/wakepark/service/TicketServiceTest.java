package ru.project.wakepark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.project.wakepark.model.Ticket;;
import ru.project.wakepark.testdata.TicketTestData;
import static ru.project.wakepark.testdata.TicketTestData.*;

@SpringBootTest
class TicketServiceTest extends AbstractServiceTest<Ticket> {

    private TicketService service;

    @Autowired
    TicketServiceTest(TicketService service) {
        super(service, TICKET_MATCHER, new TicketTestData());
        this.service = service;
    }
}

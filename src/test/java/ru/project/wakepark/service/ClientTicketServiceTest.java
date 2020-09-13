package ru.project.wakepark.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.testdata.ClientTicketTestData;
import ru.project.wakepark.testdata.TicketTestData;
import ru.project.wakepark.testdata.UserTestData;
import ru.project.wakepark.util.ClientTicketUtil;
import java.util.ArrayList;
import java.util.List;

import static ru.project.wakepark.testdata.ClientTicketTestData.*;
import static ru.project.wakepark.testdata.CompanyTestData.*;
import static ru.project.wakepark.testdata.ClientTestData.*;
import static ru.project.wakepark.testdata.TicketTestData.*;

@SpringBootTest
class ClientTicketServiceTest extends AbstractServiceTest <ClientTicket> {

    private ClientTicketService service;

    @Autowired
    public ClientTicketServiceTest(ClientTicketService service) {
        super(service, CL_TICKET_MATCHER, new ClientTicketTestData());
        this.service = service;
    }

    @Test
    void getByClient() {
        CL_TICKET_MATCHER.assertMatch(service.getAll(WAKE_ID1, CLIENT_ID1), List.of(CL_TICKET1, CL_TICKET2, CL_TICKET4));
    }

    @Test
    void getByClientActive() {
        CL_TICKET_MATCHER.assertMatch(service.getAllActive(WAKE_ID1, CLIENT_ID1), List.of(CL_TICKET1, CL_TICKET2));
    }

    @Test
    void getByTicketZero() {
        Assertions.assertThat(ClientTicketUtil.getInstance().getByTicket(service.getAllActive(WAKE_ID1, CLIENT_ID1),
                TicketTestData.TICKET1).size())
                .isEqualTo(0);
    }

    @Test
    void getByTicket() {
        CL_TICKET_MATCHER.assertMatch(ClientTicketUtil.getInstance().getByTicket(service.getAll(WAKE_ID1, CLIENT_ID1), TicketTestData.TICKET2), List.of(CL_TICKET1, CL_TICKET2));
    }

    @Test
    void createByList() {
        List<ClientTicket> list = List.of(super.testData.getNew(), super.testData.getNew());
        List<ClientTicket> newList = service.create(list, WAKE_ID1, UserTestData.USER_ID1);
        List<ClientTicket> expected = new ArrayList<>(newList);
        expected.add(CL_TICKET3);
        CL_TICKET_MATCHER.assertMatch(service.getAll(WAKE_ID1, CLIENT_ID2), expected);
    }

    @Test
    void createByQuantityDifference() {
        service.createOrDelete(WAKE_ID1, CLIENT2, TICKET1, UserTestData.USER_ID1, CLT_NEWCOUNT_CL2);
        List<ClientTicket> ctl = service.getAllActive(WAKE_ID1, CLIENT_ID2);
        Assertions.assertThat(ctl.size()).isEqualTo(CLT_NEWCOUNT_CL2);
        ctl.forEach(ct->TICKET_MATCHER.assertMatch(ct.getTicket(), TICKET1));
    }

    @Test
    void deleteByQuantityDifference() {
        List<ClientTicket> all = service.getAllActive(WAKE_ID1, CLIENT_ID1);
        Assertions.assertThat(all.size()).isNotEqualTo(CLT_NEWCOUNT_CL1);
        service.createOrDelete(WAKE_ID1, CLIENT1, TICKET2, UserTestData.USER_ID1, CLT_NEWCOUNT_CL1);
        List<ClientTicket> actual = service.getAllActive(WAKE_ID1, CLIENT_ID1);
        Assertions.assertThat(actual.size()).isEqualTo(CLT_NEWCOUNT_CL1);
    }
}
package ru.project.wakepark.testdata;

import org.assertj.core.api.Assertions;
import ru.project.wakepark.model.ClientTicket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static ru.project.wakepark.testdata.ClientTestData.CLIENT1;
import static ru.project.wakepark.testdata.ClientTestData.CLIENT3;
import static ru.project.wakepark.testdata.ClientTicketTestData.*;
import static ru.project.wakepark.testdata.CompanyTestData.WAKE_ID1;
import static ru.project.wakepark.testdata.TicketTestData.TICKET2;
import static ru.project.wakepark.testdata.UserTestData.USER1;

/** Тестовые данные из ClientTicketTestData **
 * TICKET1 - Сезонный абонемент
 * TICKET2 - Разовый абонемент
 *
 * CL_TICKET1 = new ClientTicket(10_011, WAKE_ID1, CLIENT1, TICKET2, true);
 * CL_TICKET2 = new ClientTicket(10_012, WAKE_ID1, CLIENT1, TICKET2, true);
 * CL_TICKET3 = new ClientTicket(10_013, WAKE_ID1, CLIENT2, TICKET1, true);
 * CL_TICKET4 = new ClientTicket(10_011, WAKE_ID1, CLIENT1, TICKET1, false); //погашен
 *
 * CLIENT1 + TICKET2 -> 4шт.
 * CLIENT2 + TICKET1 -> 1шт.
 * CLIENT3 + TICKET2 -> 2шт.
 */
public class QueueTestData implements TestData<ClientTicket> {

    public static void match(List<Set<ClientTicket>> actual, List<Set<ClientTicket>> expected) {

        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertThat(actual.get(i).size()).isEqualTo(expected.get(i).size());
            CL_TICKET_MATCHER.assertMatch(actual.get(i), expected.get(i));
        }
    }
    public static final int CL_TICKET_ID5 = 10_015;

    public static final int CL_TICKET_ID6 = 10_016;

    public static final int CL_TICKET_ID7 = 10_017;

    public static final int CL_TICKET_ID8 = 10_018;

    public static ClientTicket CL_TICKET5 = new ClientTicket(CL_TICKET_ID5, WAKE_ID1, CLIENT1, TICKET2, null, null, true, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(10, 0)), USER1, null, null);

    public static ClientTicket CL_TICKET6 = new ClientTicket(CL_TICKET_ID6, WAKE_ID1, CLIENT1, TICKET2, null, null, true, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(10, 0)), USER1, null, null);

    public static ClientTicket CL_TICKET7 = new ClientTicket(CL_TICKET_ID7, WAKE_ID1, CLIENT3, TICKET2, null, null, true, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(10, 0)), USER1, null, null);

    public static ClientTicket CL_TICKET8 = new ClientTicket(CL_TICKET_ID8, WAKE_ID1, CLIENT3, TICKET2, null, null, true, LocalDateTime.of(LocalDate.of(2020, Month.JUNE, 2), LocalTime.of(10, 0)), USER1, null, null);

    @Override
    public List<ClientTicket> getAll() {
        return null;
    }

    @Override
    public int getIdDelete() {
        return 0;
    }

    @Override
    public List<ClientTicket> getAllWithoutDelete() {
        return null;
    }

    @Override
    public ClientTicket getOne() {
        return null;
    }

    @Override
    public ClientTicket getNew() {
        return null;
    }

    @Override
    public ClientTicket getUpdate() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getCreatedId() {
        return 0;
    }
}

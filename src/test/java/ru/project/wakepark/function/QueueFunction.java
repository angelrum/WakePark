package ru.project.wakepark.function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.TimingExtension;
import ru.project.wakepark.service.ClientTicketService;
import ru.project.wakepark.service.QueueService;
import ru.project.wakepark.to.QueueRowTo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.project.wakepark.testdata.CompanyTestData.WAKE_ID1;
import static ru.project.wakepark.testdata.ClientTestData.*;
import static ru.project.wakepark.testdata.TicketTestData.*;
import static ru.project.wakepark.testdata.UserTestData.*;

@ActiveProfiles("test")
@ExtendWith(TimingExtension.class)
@Sql(scripts = "classpath:db/data-test.sql", config = @SqlConfig(encoding = "UTF-8"))
@SpringBootTest
public class QueueFunction {

    @Autowired
    public QueueService service;

    @Autowired
    private ClientTicketService ctService;

    @Test
    void addInQueue() throws InterruptedException {
        service.add(WAKE_ID1, CLIENT_ID1, TICKET_ID2, 2);
        printState();
        service.queueStart(WAKE_ID1, USER_ID1);
        TimeUnit.SECONDS.sleep(6);
        printState();
    }

    void printState() {
        List<QueueRowTo> all = service.getAll(WAKE_ID1);
        if (CollectionUtils.isEmpty(all))
            System.out.println("Queue is empty");
        all.forEach(System.out::println);
    }

}

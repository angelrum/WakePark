package ru.project.wakepark.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.TimingExtension;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.repository.QueueRepository;
import ru.project.wakepark.testdata.QueueTestData;

import java.util.*;

import static ru.project.wakepark.testdata.ClientTicketTestData.*;
import static ru.project.wakepark.testdata.CompanyTestData.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(TimingExtension.class)
class QueueServiceTest {

    @Autowired
    protected QueueRepository repository;

    @BeforeEach
    void init() {
        System.out.println("Before each");
        LinkedList<Set<ClientTicket>> active = repository.getActiveQueue(WAKE_ID1);
        if (!CollectionUtils.isEmpty(active)) {
            active.clear();
        }
        LinkedList<Set<ClientTicket>> stopped = repository.getStoppedQueue(WAKE_ID1);
        if (!CollectionUtils.isEmpty(stopped)) {
            stopped.clear();
        }

        Set<ClientTicket> list = new HashSet<>();
        list.add(CL_TICKET1);
        repository.add(WAKE_ID1, list);
    }

    @AfterEach
    void after() {
        System.out.println("***##    Result    ##***");
        printState();
    }

    private void printState() {
        LinkedList<Set<ClientTicket>> active = repository.getActiveQueue(WAKE_ID1);
        if (!CollectionUtils.isEmpty(active)) {
            System.out.println("##--- Active queue ---##");
            print(active);
        }
        LinkedList<Set<ClientTicket>> stopped = repository.getStoppedQueue(WAKE_ID1);
        if (!CollectionUtils.isEmpty(stopped)) {
            System.out.println("##--- Stopped queue ---##");
            print(stopped);
        }
    }

    private void print(LinkedList<Set<ClientTicket>> active) {
        int n = 0;
        for (Set<ClientTicket> list : active) {
            n++;
            final int s = n;
            list.forEach(ct-> System.out.printf("%s : %s\n", s, ct.toString()));
        }
    }

    @Test
    void addInQueue() {
        printState();

        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET3)));
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET2)));
        LinkedList<Set<ClientTicket>> activeQueue = repository.getActiveQueue(WAKE_ID1);
        QueueTestData.match(activeQueue, List.of(Set.of(CL_TICKET1, CL_TICKET2), Set.of(CL_TICKET3)));
    }

    @Test
    void insertDublicateRow() {
        printState();
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET1)));
        QueueTestData.match(repository.getActiveQueue(WAKE_ID1), List.of(Set.of(CL_TICKET1)));
    }

    @Test
    void delete() {
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET3)));
        printState();

        repository.removeFromActive(WAKE_ID1, CL_TICKET1);
        LinkedList<Set<ClientTicket>> activeQueue = repository.getActiveQueue(WAKE_ID1);
        QueueTestData.match(activeQueue, List.of(new HashSet<>(Arrays.asList(CL_TICKET3))));
    }

    @Test
    void raiseUp() {
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET3)));
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET2)));
        printState();

        repository.moveUp(WAKE_ID1, CL_TICKET3);
        LinkedList<Set<ClientTicket>> activeQueue = repository.getActiveQueue(WAKE_ID1);
        QueueTestData.match(activeQueue, List.of(Set.of(CL_TICKET3), Set.of(CL_TICKET1, CL_TICKET2)));
    }

    @Test
    void raiseDown() {
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET3)));
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET2)));
        printState();

        repository.moveDown(WAKE_ID1, CL_TICKET1);
        LinkedList<Set<ClientTicket>> activeQueue = repository.getActiveQueue(WAKE_ID1);
        QueueTestData.match(activeQueue, List.of(Set.of(CL_TICKET3), Set.of(CL_TICKET1, CL_TICKET2)));
    }

    @Test
    void moveToStoppedQueue() {
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET3)));
        repository.add(WAKE_ID1, new HashSet<>(Arrays.asList(CL_TICKET2)));
        printState();
        repository.moveToStopped(WAKE_ID1, CL_TICKET1);
        QueueTestData.match(repository.getActiveQueue(WAKE_ID1), List.of(Set.of(CL_TICKET3)));
        QueueTestData.match(repository.getStoppedQueue(WAKE_ID1), List.of(Set.of(CL_TICKET1, CL_TICKET2)));
    }

    @Test
    void moveToActiveQueue() {
        moveToStoppedQueue();
        repository.moveToActive(WAKE_ID1, CL_TICKET1);
        QueueTestData.match(repository.getActiveQueue(WAKE_ID1), List.of(Set.of(CL_TICKET3), Set.of(CL_TICKET1, CL_TICKET2)));
    }
}
package ru.project.wakepark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.repository.QueueRepository;
import ru.project.wakepark.to.ControlQueueRow;
import ru.project.wakepark.to.QueueRowTo;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.project.wakepark.util.QueueUtil.*;
import static ru.project.wakepark.util.ValidationUtil.*;

@Service
public class QueueService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private ClientTicketService ctService;

    private QueueRepository repository;

    private TimerService watch;

    private QueueControl control;

    public QueueService(QueueRepository repository, ClientTicketService ctService, TimerService watch, QueueControl control) {
        this.repository = repository;
        this.ctService = ctService;
        this.control = control;
        this.watch = watch;
    }

    public void add(int companyId, int clientId, int ticketId, int count) {
        log.info("add client ticket in active queue. Company {}, client {}, ticket {} and count {}", companyId, clientId, ticketId, count);

        List<ClientTicket> all = ctService.getWithoutQueue(companyId, clientId, ticketId);
        checkNotFoundWithId(all, companyId, clientId, ticketId);
        checkNotEqualByCount(all, count);
        repository.add(companyId, all.stream().limit(count).collect(Collectors.toSet()));
        //control.needUpdate();
    }

    public void delete(int companyId, int ctId) {
        log.info("delete client ticket list from queue. Company {} and client ticket {}", companyId, ctId);
        ClientTicket ct = checkNotFoundWithId(ctService.get(ctId, companyId), ctId, companyId);
        if (!repository.removeFromActive(companyId, ct))
            repository.removeFromStopped(companyId, ct);
        //control.needUpdate();
    }

    public List<QueueRowTo> getAll(int companyId) {
        log.info("get queue list for company {}", companyId);
        List<QueueRowTo> result = new ArrayList<>();
        LinkedList<Set<ClientTicket>> activeQueue = repository.getActiveQueue(companyId);
        LinkedList<Set<ClientTicket>> stoppedQueue = repository.getStoppedQueue(companyId);
        if (!CollectionUtils.isEmpty(activeQueue)) result.addAll(createTos(activeQueue, watch.isStart(companyId)));
        if (!CollectionUtils.isEmpty(stoppedQueue)) result.addAll(createTos(stoppedQueue, ControlQueueRow.PLAY, ControlQueueRow.NONE));

        return result;
    }

    public List<QueueRowTo> getOnlyActive(int companyId) {
        log.info("get active queue list for company {}", companyId);
        List<QueueRowTo> result = new ArrayList<>();
        LinkedList<Set<ClientTicket>> activeQueue = repository.getActiveQueue(companyId);
        if (!CollectionUtils.isEmpty(activeQueue)) result.addAll(createTos(activeQueue, watch.isStart(companyId)));
        return result;
    }

    public boolean raiseUp(int companyId, int ctId) {
        log.info("raise the line up. Client ticket {} and company {}", ctId, companyId);
        ClientTicket ct = checkNotFoundWithId(ctService.get(ctId, companyId), ctId, companyId);
        //control.needUpdate();
        return repository.moveUp(companyId, ct);
    }

    public boolean raiseDown(int companyId, int ctId) {
        log.info("raise the line down. Client ticket {} and company {}", ctId, companyId);
        ClientTicket ct = checkNotFoundWithId(ctService.get(ctId, companyId), ctId, companyId);
        //control.needUpdate();
        return repository.moveDown(companyId, ct);
    }

    public boolean moveToStoppedQueue(int companyId, int ctId) {
        log.info("move to stopped list. Client ticket {} and company {}", ctId, companyId);
        ClientTicket ct = checkNotFoundWithId(ctService.get(ctId, companyId), ctId, companyId);
        //control.needUpdate();
        return repository.moveToStopped(companyId, ct);
    }

    public boolean moveToActiveQueue(int companyId, int ctId) {
        log.info("move to active list. Client ticket {} and company {}", ctId, companyId);
        ClientTicket ct = checkNotFoundWithId(ctService.get(ctId, companyId), ctId, companyId);
        //control.needUpdate();
        return repository.moveToActive(companyId, ct);
    }


    public void queueStart(int companyId, int userId) {
        log.info("Start queue. Company {}", companyId);
        ClientTicket ct = control.queueStart(companyId, userId);
        int durationInSeconds = LocalTime.of(0, ct.getTicket().getDuration()).toSecondOfDay();
        log.info("Need start timer on {} sec", durationInSeconds);
        watch.startTimer(companyId, userId, durationInSeconds);
    }

    public void queueStop(int companyId, int userId) {
        if (watch.isStart(companyId)) {
            watch.stopTimer(companyId);
            control.queueStop(companyId, userId);
        }
    }

    public void queuePause(int companyId, int userId) {
        if (watch.isStart(companyId)) {
            log.info("Clock was start");
            final LocalTime time = control.queueOnPause(companyId, watch.getDuration(companyId));
            if (Objects.nonNull(time)) {
                log.info("fixation pause time in sec {}", time.toSecondOfDay());
                watch.pauseTimer(companyId, time.toSecondOfDay());
            }
        }
    }

}

package ru.project.wakepark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.project.wakepark.event.EventManager;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.ClientTicketStory;
import ru.project.wakepark.model.Pass;
import ru.project.wakepark.repository.QueueRepository;
import ru.project.wakepark.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static ru.project.wakepark.util.ValidationUtil.checkNotFound;
import static ru.project.wakepark.util.ValidationUtil.checkNotFoundWithId;

@Service
public class QueueControl {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private QueueRepository repository;

    private StoryService storyService;

    private ClientTicketService ctService;

    private EventManager em;

    public QueueControl(QueueRepository repository, StoryService storyService, ClientTicketService ctService, EventManager em) {
        this.repository = repository;
        this.storyService = storyService;
        this.ctService = ctService;
        this.em = em;
    }

    public ClientTicket queueStart(int companyId, int userId) {
        log.info("Start queue. Company {}", companyId);
        ClientTicket ct = checkNotFound(repository.getFistTicket(companyId), "Not found first pass from queue");
        ctService.setExpirationDate(companyId, ct, LocalDate.now(), userId);
        storyService.setStoryStart(companyId, ct);
        return ct;
    }

    public void queueStop(int companyId, int userId) {
        log.info("Stop queue row. Company {}", companyId);
        closeQueueRow(companyId, userId);
        em.send(companyId);
    }

    public LocalTime queueOnPause(int companyId, int duration) {
        log.info("Queue on pause. Company {}", companyId);
        ClientTicket ct = repository.getFistTicket(companyId);
        if (Objects.nonNull(ct)) {
            final ClientTicketStory openStory = checkNotFoundWithId(storyService.getOpenStory(companyId, ct.getId()), ct.getId(), companyId);
            storyService.setStoryCancellation(companyId, ct);
            LocalTime start = openStory.getStartTime();
            LocalTime end = LocalTime.now();
            LocalTime from = LocalTime.ofSecondOfDay(duration);
            final LocalTime time = DateTimeUtil.remainderOfTime(start, end, from);
            log.info("time start {}, time end {}, left {}", start, end, time);
            return time;
        }
        return null;
    }

    private void closeQueueRow(int companyId, int userId) {
        ClientTicket ct = checkNotFound(repository.reedemTicket(companyId), "Not found ticket for cancellation");
        storyService.setStoryCancellation(companyId, ct);
        if (ct.getTicket().getPass().equals(Pass.SINGLE)) {
            ct.setActive(false);
            ctService.update(ct, companyId, userId);
        }
    }
}

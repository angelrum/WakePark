package ru.project.wakepark.web.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.View;
import ru.project.wakepark.event.EventManager;
import ru.project.wakepark.event.MailingOfActiveQueue;
import ru.project.wakepark.event.MailingOfQueue;
import ru.project.wakepark.event.MailingOfStateQueue;
import ru.project.wakepark.service.QueueService;
import ru.project.wakepark.service.QueueStateService;
import ru.project.wakepark.to.ClientTicketTo;
import ru.project.wakepark.to.ControlQueueRow;
import ru.project.wakepark.to.QueueRowTo;

import javax.validation.constraints.NotNull;
import java.util.List;
import static ru.project.wakepark.web.SecurityUtil.*;

@RestController
@RequestMapping("/ajax/controller/queue")
public class MainUIController {

    private QueueService service;

    private EventManager em;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public MainUIController(QueueService service, QueueStateService stateService, MessageSendingOperations<String> message, EventManager em) {
        this.service = service;
        this.em = em;
        em.subscribe("queue", new MailingOfQueue(message, service));
        em.subscribe("state", new MailingOfStateQueue(message, stateService));
        em.subscribe("queue/active", new MailingOfActiveQueue(message, service));
    }

//    @PostConstruct
//    @Profile("dev")
//    public void init() {
//        service.add(AuthorizedUser.getCompanyId(), 10_003, 10_007, 2);
//        service.add(AuthorizedUser.getCompanyId(), 10_004, 10_006, 1);
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QueueRowTo> getAll() {
        return service.getAll(authCompanyId());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void add(@Validated(View.Web.class) ClientTicketTo to) {
        service.add(authCompanyId(), to.getClientId(), to.getTicketId(), to.getCount());
        em.send(authCompanyId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Integer ctId) {
        service.delete(authCompanyId(), ctId);
        em.send(authCompanyId());
    }

    @GetMapping(value = "/control")
    public void move(@RequestParam @NotNull ControlQueueRow move, @NotNull Integer ctId) {
        switch (move) {
            case UP:    service.raiseUp(authCompanyId(), ctId);
                break;
            case DOWN:  service.raiseDown(authCompanyId(), ctId);
                break;
            case PLAY:  service.moveToActiveQueue(authCompanyId(), ctId);
                break;
            case PAUSE: service.moveToStoppedQueue(authCompanyId(), ctId);
                break;
            case DELETE: service.delete(authCompanyId(), ctId);
        }
        em.send(authCompanyId());
    }

//    private void sendQueueState() {
//        message.convertAndSend("/topic/queue", service.getAll(AuthorizedUser.getCompanyId()));
//        message.convertAndSend("/topic/state", stateService.getState(AuthorizedUser.getCompanyId()));
//    }
}

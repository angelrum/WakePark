package ru.project.wakepark.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.event.EventManager;
import ru.project.wakepark.service.QueueService;
import ru.project.wakepark.service.QueueStateService;
import ru.project.wakepark.to.QueueState;
import static ru.project.wakepark.web.SecurityUtil.*;

@RestController
@RequestMapping("/queue")
public class QueueRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private QueueService service;

    private QueueStateService stateService;

    private EventManager em;

    public QueueRestController(QueueService service, QueueStateService stateService, EventManager em) {
        this.service = service;
        this.stateService = stateService;
        this.em = em;
    }

    @GetMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueueState queueState() {
        log.info("get state for company {}", authCompanyId());
        return stateService.getState(authCompanyId());
    }

    @MessageMapping({"/event"})
    @SendTo("/topic/state")
    public QueueState processEvent(@RequestBody QueueState state) throws Exception {
        log.info("queue change, click control button {}. Company {}", state.getState(), AuthorizedUser.DEF_COMPANY); //authCompanyId() пока не работает
        switch (state.getState()) {
            case PLAY: service.queueStart(AuthorizedUser.DEF_COMPANY, AuthorizedUser.DEF_USER);
                break;
            case PAUSE: service.queuePause(AuthorizedUser.DEF_COMPANY, AuthorizedUser.DEF_USER);
                break;
            case STOP: service.queueStop(AuthorizedUser.DEF_COMPANY, AuthorizedUser.DEF_USER);
                break;
        }
        em.send(AuthorizedUser.DEF_COMPANY, "queue");
        return stateService.getState(AuthorizedUser.DEF_COMPANY);
    }
}

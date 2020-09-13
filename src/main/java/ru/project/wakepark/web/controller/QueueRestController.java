package ru.project.wakepark.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.service.QueueControl;
import ru.project.wakepark.service.QueueService;
import ru.project.wakepark.service.QueueStateService;
import ru.project.wakepark.to.QueueRowTo;
import ru.project.wakepark.to.QueueState;
import ru.project.wakepark.util.ControlQueue;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/queue")
public class QueueRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private QueueService service;

    private QueueControl control;

    private QueueStateService stateService;

    public QueueRestController(QueueService service, QueueControl control, QueueStateService stateService) {
        this.service = service;
        this.control = control;
        this.stateService = stateService;
    }

    @GetMapping(value = "/stream")
    public Flux<List<QueueRowTo>> feed(@RequestParam String uuid) {
        log.info("register uuid={}", uuid);
        control.register(uuid);
        return Flux.interval(Duration.ofSeconds(1))
                .onBackpressureDrop()
                .filter(l -> control.check(uuid))
                .map(this::getUpdateRow);
    }

    @GetMapping(value = "/stream/remove")
    public void remove(@RequestParam String uuid) {
        log.info("remove uuid={}", uuid);
        control.unregister(uuid);

    }

    private List<QueueRowTo> getUpdateRow(long interval) {
        List<QueueRowTo> all = service.getAll(AuthorizedUser.getCompanyId());
        log.info("get list with count {}", all.size());
        return all;
    }

    @GetMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueueState queueState() {
        log.info("get state for company {}", AuthorizedUser.getCompanyId());
        return stateService.getState(AuthorizedUser.getCompanyId());
    }

    @GetMapping
    public void queueControl(@RequestParam @NotNull ControlQueue control) {
        log.info("queue change, click control button {}. Company {}", control, AuthorizedUser.getCompanyId());
        switch (control) {
            case PLAY: service.queueStart(AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
                break;
            case PAUSE: service.queuePause(AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
                break;
            case STOP: service.queueStop(AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
                break;
        }
    }
}

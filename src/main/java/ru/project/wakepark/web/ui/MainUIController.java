package ru.project.wakepark.web.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.View;
import ru.project.wakepark.service.QueueService;
import ru.project.wakepark.to.ClientTicketTo;
import ru.project.wakepark.to.ControlQueueRow;
import ru.project.wakepark.to.QueueRowTo;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/ajax/controller/queue")
public class MainUIController {

    private QueueService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public MainUIController(QueueService service) {
        this.service = service;
        //service.add(AuthorizedUser.getCompanyId(), 10_003, 10_007, 2);
        //service.add(AuthorizedUser.getCompanyId(), 10_004, 10_006, 1);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QueueRowTo> getAll() {
        return service.getAll(AuthorizedUser.getCompanyId());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void add(@Validated(View.Web.class) ClientTicketTo to) {
        service.add(AuthorizedUser.getCompanyId(), to.getClientId(), to.getTicketId(), to.getCount());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Integer ctId) {
        service.delete(AuthorizedUser.getCompanyId(), ctId);
    }

    @GetMapping(value = "/control")
    public void move(@RequestParam @NotNull ControlQueueRow move, @NotNull Integer ctId) {
        switch (move) {
            case UP:    service.raiseUp(AuthorizedUser.getCompanyId(), ctId);
                break;
            case DOWN:  service.raiseDown(AuthorizedUser.getCompanyId(), ctId);
                break;
            case PLAY:  service.moveToActiveQueue(AuthorizedUser.getCompanyId(), ctId);
                break;
            case PAUSE: service.moveToStoppedQueue(AuthorizedUser.getCompanyId(), ctId);
                break;
            case DELETE: service.delete(AuthorizedUser.getCompanyId(), ctId);
        }
    }
}

package ru.project.wakepark.web.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.View;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.service.ClientService;
import ru.project.wakepark.service.ClientTicketService;
import ru.project.wakepark.service.TicketService;
import ru.project.wakepark.to.ClientTicketTo;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.project.wakepark.util.ClientTicketUtil.*;
import static ru.project.wakepark.web.SecurityUtil.*;

@RestController
@RequestMapping("/ajax/controller/client/tickets")
public class ClientTicketUIController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private ClientTicketService service;

    private ClientService clService;

    private TicketService tService;

    @Autowired
    public ClientTicketUIController(ClientTicketService service, ClientService clService, TicketService tService) {
        this.service = service;
        this.clService = clService;
        this.tService = tService;
    }

    @GetMapping(value = "/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientTicketTo> getClientTickets(@PathVariable Integer clientId) {
        return getInstance()
                .getTos(service.getWithoutQueue(authCompanyId(), clientId));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientTicketTo> getWithFilter(@RequestParam Integer clientId,
                                              @RequestParam @Nullable LocalTime startTime,
                                              @RequestParam @Nullable LocalTime endTime) {
        List<ClientTicket> ctl = service.getWithoutQueue(authCompanyId(), clientId);
        if (Objects.nonNull(ctl)) {
            return getInstance().getTosWithFilter(ctl, startTime, endTime);
        }
        return null;
    }

    @GetMapping(value = "/")
    public ClientTicketTo get(
            @RequestParam @Nullable Integer id,
            @RequestParam @Nullable Integer clientId) {
        log.info("get client ticket by id {} for client {} and company {}", id, clientId, authCompanyId());
        if (Objects.isNull(id) || Objects.isNull(clientId)){
            return null;
        }
        return getInstance().createTo(service.get(authCompanyId(), id, clientId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@Validated(View.Web.class) ClientTicketTo to) {
        log.info("add new client ticket by client {}, ticket {} and company {}", to.getClientId(), to.getTicketId(), authCompanyId());
        ClientTicket ct = getInstance().createModel(to, clService.get(to.getClientId(), authCompanyId()), tService.get(to.getTicketId(), authCompanyId()));
        if (ct.isNew()) {
            service.create(ct, authCompanyId(), authUserId(), to.getCount());
        }
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) ClientTicketTo to) {
        log.info("update client ticket by client {}, ticket {} and company {}", to.getClientId(), to.getTicketId(), authCompanyId());

        service.createOrDelete(authCompanyId(),
                clService.get(to.getClientId(), authCompanyId()),
                tService.get(to.getTicketId(), authCompanyId()),
                authUserId(), to.getCount());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Validated(View.Web.class) ClientTicketTo to) {
        log.info("delete all client pass by ticket {}, where client {} and company {}", to.getTicketId(), to.getClientId(), authCompanyId());
        List<ClientTicket> all = service.getAll(authCompanyId(), to.getClientId());
        service.delete(
                getInstance().getByTicket(all, tService.get(to.getTicketId(), authCompanyId())),
                authCompanyId());

    }
}

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
import ru.project.wakepark.model.Client;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.service.ClientService;
import ru.project.wakepark.service.ClientTicketService;
import ru.project.wakepark.service.TicketService;
import ru.project.wakepark.to.ClientTicketTo;
import ru.project.wakepark.util.ClientTicketUtil;

import java.util.List;

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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientTicketTo> getClientTickets(@PathVariable Integer id) {
        List<ClientTicketTo> res = ClientTicketUtil.getInstance()
                .getTos(service.getAllByClientAndActive(AuthorizedUser.getCompanyId(), id));
        log.info("get all tickets for client {} with count {}", id, res.size());
        return ClientTicketUtil.getInstance()
                .getTos(service.getAllByClientAndActive(AuthorizedUser.getCompanyId(), id));
    }

    @GetMapping(value = "/")
    public ClientTicketTo get(
            @RequestParam Integer id,
            @RequestParam Integer clientId) {
        log.info("get client ticket by id {} for client {} and company {}", id, clientId, AuthorizedUser.getCompanyId());
        return ClientTicketUtil.getInstance().createTo(service.get(AuthorizedUser.getCompanyId(), id, clientId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@Validated(View.Web.class) ClientTicketTo to) {
        log.info("add new client ticket by client {}, ticket {} and company {}", to.getClientId(), to.getTicketId(), AuthorizedUser.getCompanyId());
        ClientTicket ct = ClientTicketUtil.getInstance().createModel(to, clService.get(to.getClientId(), AuthorizedUser.getCompanyId()), tService.get(to.getTicketId(), AuthorizedUser.getCompanyId()));
        if (ct.isNew()) {
            service.create(ct, AuthorizedUser.getCompanyId(), AuthorizedUser.getId(), to.getCount());
        }
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) ClientTicketTo to) {
        log.info("update client ticket by client {}, ticket {} and company {}", to.getClientId(), to.getTicketId(), AuthorizedUser.getCompanyId());

        service.createOrDelete(AuthorizedUser.getCompanyId(),
                clService.get(to.getClientId(), AuthorizedUser.getCompanyId()),
                tService.get(to.getTicketId(), AuthorizedUser.getCompanyId()),
                AuthorizedUser.getId(), to.getCount());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Validated(View.Web.class) ClientTicketTo to) {
        log.info("delete all client pass by ticket {}, where client {} and company {}", to.getTicketId(), to.getClientId(), AuthorizedUser.getCompanyId());
        List<ClientTicket> all = service.getAllByClient(AuthorizedUser.getCompanyId(), to.getClientId());
        service.delete(
                ClientTicketUtil.getInstance().getByTicket(all, tService.get(to.getTicketId(), AuthorizedUser.getCompanyId())),
                AuthorizedUser.getCompanyId());

    }
}

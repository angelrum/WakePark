package ru.project.wakepark.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.View;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.service.TicketService;
import ru.project.wakepark.to.TicketTo;
import java.util.List;

import static ru.project.wakepark.util.TicketUtil.getInstance;

@RestController
@RequestMapping("/ajax/controller/tickets")
public class TicketsUIController {

    private TicketService service;

    @Autowired
    public TicketsUIController(TicketService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TicketTo> getAll() {
        return getInstance().getTos(service.getAll(AuthorizedUser.getCompanyId()));
    }

    @GetMapping(value = "/{id}")
    public TicketTo get(@PathVariable Integer id) {
        return getInstance().createTo(service.get(id, AuthorizedUser.getCompanyId()));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Validated(View.Web.class) Ticket ticket) {
        if (ticket.isNew())
            service.create(ticket, AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
        else
            service.update(ticket, AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
    }

    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id, AuthorizedUser.getCompanyId());
    }

}

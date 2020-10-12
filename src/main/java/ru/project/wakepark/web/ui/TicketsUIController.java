package ru.project.wakepark.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.service.TicketService;
import ru.project.wakepark.to.TicketTo;
import ru.project.wakepark.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.project.wakepark.util.TicketUtil.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/ajax/controller/tickets")
public class TicketsUIController extends AbstractUIController<Ticket, TicketTo> {

    private TicketService service;

    @Autowired
    public TicketsUIController(TicketService service) {
        super(service, getInstance());
        this.service = service;
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TicketTo> getBetween(
            @RequestParam @Nullable String active,
            @RequestParam @Nullable String equipment,
            @RequestParam @Nullable LocalTime timeStart,
            @RequestParam @Nullable LocalTime timeEnd,
            @RequestParam @Nullable LocalDate dateStart,
            @RequestParam @Nullable LocalDate dateEnd) {
        List<Ticket> tickets = service.getWithFilter(
                SecurityUtil.authCompanyId(),
                getInstance().getBooleanFromString(active), getInstance().getBooleanFromString(equipment),
                timeStart, timeEnd, dateStart, dateEnd);

        return getInstance().getTos(tickets);
    }

}

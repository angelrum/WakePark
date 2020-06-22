package ru.project.wakepark.service;

import org.springframework.stereotype.Service;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.repository.CrudTicketRepository;

@Service
public class TicketService extends AbstractService<Ticket> {

    private final CrudTicketRepository repository;

    public TicketService(CrudTicketRepository repository) {
        super(repository);
        this.repository = repository;
    }
}

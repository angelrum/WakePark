package ru.project.wakepark.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.repository.CrudClientTicketRepository;
import ru.project.wakepark.repository.QueueRepository;
import ru.project.wakepark.util.ClientTicketUtil;
import ru.project.wakepark.util.TicketUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.project.wakepark.util.ValidationUtil.*;
import static ru.project.wakepark.util.ClientTicketUtil.getInstance;

@Service
public class ClientTicketService extends AbstractService<ClientTicket> {

    private CrudClientTicketRepository repository;

    private QueueRepository qRepository;

    public ClientTicketService(CrudClientTicketRepository repository, QueueRepository qRepository) {
        super(repository);
        this.repository = repository;
        this.qRepository = qRepository;
    }

    public List<ClientTicket> getAll(int companyId, int clientId) {
        return repository.getAll(companyId, clientId);
    }

    public List<ClientTicket> getAllActive(int companyId, int clientId) {
        return repository.getAllActive(companyId, clientId);
    }

    public List<ClientTicket> getAllActive(int companyId, int clientId, int ticketId) {
        return repository.getAllActive(companyId, clientId, ticketId);
    }

    public List<ClientTicket> getWithoutQueue(int companyId, int clientId, int ticketId) {
        List<ClientTicket> tickets = getAllActive(companyId, clientId, ticketId);
        if (!CollectionUtils.isEmpty(tickets)) {
            getInstance().filterList(tickets, qRepository.getActiveQueue(companyId));
            getInstance().filterList(tickets, qRepository.getStoppedQueue(companyId));
        }
        return tickets;
    }

    public List<ClientTicket> getWithoutQueue(int companyId, int clientId) {
        List<ClientTicket> ct = getAllActive(companyId, clientId);
        if (!CollectionUtils.isEmpty(ct)) {
            getInstance().filterList(ct, qRepository.getActiveQueue(companyId));
            getInstance().filterList(ct, qRepository.getStoppedQueue(companyId));
        }
        return ct;
    }

    public List<ClientTicket> create(ClientTicket ct, int companyId, int userId, int count) {
        List<ClientTicket> result = new ArrayList<>();
        int step = 0;
        do {
            ClientTicket cr = new ClientTicket(ct);
            cr.setId(null);
            result.add(super.create(cr, companyId, userId));
            step++;
        } while (count != step);
        return result;
    }

    public ClientTicket get(int companyId, int id, int clientId) {
        return checkNotFoundWithId(repository.getOneByIdAndClient(companyId, id, clientId), id, companyId);
    }

    public boolean setExpirationDate(int companyId, ClientTicket ct, LocalDate start, int userId) {
        LocalDate end = TicketUtil.getInstance().getTicketExpirationDate(start, ct.getTicket());
        if (Objects.isNull(end)) return false;
        ct.setDateStart(start);
        ct.setDateEnd(end);
        return Objects.nonNull(repository.save(ct, companyId, userId));
    }

    public List<ClientTicket> create(List<ClientTicket> ctl, int companyId, int userId) {
        ctl.forEach(ct->super.create(ct, companyId, userId));
        return ctl;
    }

    public void createOrDelete(int companyId, Client c, Ticket t, int userId, int count) {
        List<ClientTicket> all = checkNotFoundWithId(getAllActive(companyId, c.getId()), c.getId(), companyId);
        all = ClientTicketUtil.getInstance().getByTicket(all, t);
        if (all.size() > count) {
            List<ClientTicket> list =getInstance().getRemoveList(all, count);
            if (!CollectionUtils.isEmpty(list)) delete(list, companyId);
        }
        else if (all.size() < count) {
            List<ClientTicket> list = getInstance().getAddList(c, t, count - all.size());
            if (!CollectionUtils.isEmpty(list)) create(list, companyId, userId);
        }
    }

    public void delete(List<ClientTicket> ctl, int companyId) {
        ctl.forEach(ct->super.delete(ct.id(), companyId));
    }

}

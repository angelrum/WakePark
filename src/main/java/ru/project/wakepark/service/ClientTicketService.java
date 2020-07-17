package ru.project.wakepark.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.repository.CrudClientTicketRepository;
import ru.project.wakepark.util.ClientTicketUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.project.wakepark.util.ValidationUtil.*;
import static ru.project.wakepark.util.ClientTicketUtil.getInstance;

@Service
public class ClientTicketService extends AbstractService<ClientTicket> {

    private CrudClientTicketRepository repository;

    public ClientTicketService(CrudClientTicketRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<ClientTicket> getAllByClient(int companyId, int clientId) {
        return repository.getAllByClient(companyId, clientId);
    }

    public List<ClientTicket> getAllByClientAndActive(int companyId, int clientId) {
        return repository.getAllByClientAndActive(companyId, clientId);
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

    public List<ClientTicket> create(List<ClientTicket> ctl, int companyId, int userId) {
        ctl.forEach(ct->super.create(ct, companyId, userId));
        return ctl;
    }

    public void createOrDelete(int companyId, Client c, Ticket t, int userId, int count) {
        List<ClientTicket> all = checkNotFoundWithId(getAllByClientAndActive(companyId, c.getId()), c.getId(), companyId);
        all = ClientTicketUtil.getInstance().getByTicket(all, t);
        if (all.size() > count) {
            List<ClientTicket> list =getInstance().getRemoveList(all, count);
            if (!CollectionUtils.isEmpty(list))
                delete(list, companyId);
        }
        else if (all.size() < count) {
            List<ClientTicket> list = getInstance().getAddList(c, t, count - all.size());
            if (!CollectionUtils.isEmpty(list))
                create(list, companyId, userId);
        }
    }

    public void delete(List<ClientTicket> ctl, int companyId) {
        ctl.forEach(ct->super.delete(ct.id(), companyId));
    }

}

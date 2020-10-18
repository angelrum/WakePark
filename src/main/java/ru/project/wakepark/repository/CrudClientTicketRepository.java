package ru.project.wakepark.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.repository.jpa.JpaClientTicketRepository;
import ru.project.wakepark.repository.jpa.JpaCompanyRepository;
import ru.project.wakepark.repository.jpa.JpaUserRepository;
import ru.project.wakepark.util.DateTimeUtil;
import ru.project.wakepark.util.TicketUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class CrudClientTicketRepository extends AbstractDateChangedRepository <ClientTicket> {

    private JpaClientTicketRepository repository;

    public CrudClientTicketRepository(JpaClientTicketRepository repository,
                                      JpaCompanyRepository companyRepository,
                                      JpaUserRepository userRepository) {
        super(repository, companyRepository, userRepository);
        this.repository = repository;
    }

    public List<ClientTicket> getAll(int companyId, int clientId) {
        return repository.findWithClientAndTicketByClient(companyId, clientId);
    }

    public List<ClientTicket> getAllActive(int companyId, int clientId) {
        return filterByDate(repository.findWithClientAndTicketByClientAndActive(companyId, clientId));
    }

    public List<ClientTicket> getAllActive(int companyId, int clientId, int ticketId) {
        return filterByDate(repository.findByClientAndTicket(companyId, clientId, ticketId));
    }

    public ClientTicket getOneByIdAndClient(int companyId, int id, int clientId) {
        return DataAccessUtils.singleResult(repository.findByIdAndClient(companyId, clientId, id));
    }

    private List<ClientTicket> filterByDate(List<ClientTicket> clientTickets) {
        return clientTickets.stream()
                .filter(cl->TicketUtil.getInstance().checkActualByDate(cl.getTicket()))
                .collect(Collectors.toList());
    }
}

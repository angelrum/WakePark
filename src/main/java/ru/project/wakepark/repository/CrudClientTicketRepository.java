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

import java.util.List;

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
        return repository.findWithClientAndTicketByClientAndActive(companyId, clientId);
    }

    public List<ClientTicket> getAllActive(int companyId, int clientId, int ticketId) {
        return repository.findByClientAndTicket(companyId, clientId, ticketId);
    }

    public ClientTicket getOneByIdAndClient(int companyId, int id, int clientId) {
        return DataAccessUtils.singleResult(repository.findByIdAndClient(companyId, clientId, id));
    }
}

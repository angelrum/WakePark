package ru.project.wakepark.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.repository.jpa.JpaCompanyRepository;
import ru.project.wakepark.repository.jpa.JpaTicketRepository;
import ru.project.wakepark.repository.jpa.JpaUserRepository;

@Repository
@Transactional(readOnly = true)
public class CrudTicketRepository extends AbstractDateChangedRepository<Ticket> {

    private JpaTicketRepository repository;

    public CrudTicketRepository(JpaTicketRepository repository,
                                JpaCompanyRepository companyRepository,
                                JpaUserRepository userRepository) {
        super(repository, companyRepository, userRepository);
        this.repository = repository;
    }
}

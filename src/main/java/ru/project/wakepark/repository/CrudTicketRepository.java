package ru.project.wakepark.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.repository.jpa.JpaCompanyRepository;
import ru.project.wakepark.repository.jpa.JpaTicketRepository;
import ru.project.wakepark.repository.jpa.JpaUserRepository;
import ru.project.wakepark.util.TicketUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

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

    public List<Ticket> getAllEnable(int companyId) {
        return repository.findOnlyActive(companyId, LocalDate.now().getYear());
    }

    public List<Ticket> getBetweenHalfOpen(int companyId, LocalDate startDate, LocalDate endDate ) {
        if (Objects.isNull(startDate) && Objects.isNull(endDate))
            return repository.findByCompanyIdAndYearOrderByCreatedOn(companyId, LocalDate.now().getYear());

        return repository.findBetweenHalfOpen(companyId, LocalDate.now().getYear(), startDate, endDate);
    }

}

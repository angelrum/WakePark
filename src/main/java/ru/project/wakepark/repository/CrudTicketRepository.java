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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        final List<Ticket> actual = repository.findOnlyActive(companyId, LocalDate.now().getYear());
        return filteredByCurrentDate(actual);
    }

    public List<Ticket> getBetweenHalfOpen(int companyId, LocalDate startDate, LocalDate endDate ) {
        List<Ticket> actual;
        int year = LocalDate.now().getYear();
        if (Objects.isNull(startDate)
                && Objects.isNull(endDate))
            actual = repository.findByCompanyIdAndYearOrderByCreatedOn(companyId, year);
        else actual = repository.findBetweenHalfOpen(companyId, year, startDate, endDate);

        return filteredByCurrentDate(actual);
    }

    private List<Ticket> filteredByCurrentDate(List<Ticket> tickets) {
        return tickets.stream()
                .filter(t->TicketUtil.getInstance().checkActualByDate(t))
                .collect(Collectors.toList());
    }

}

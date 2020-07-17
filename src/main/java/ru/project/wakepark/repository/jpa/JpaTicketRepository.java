package ru.project.wakepark.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.repository.commons.CommonDateRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaTicketRepository extends CommonDateRepository<Ticket> {

    @Query("SELECT t FROM Ticket t WHERE t.companyId=:companyId AND t.year=:year AND t.enable = true ")
    List<Ticket> findOnlyActive(@Param("companyId") int companyId, @Param("year") int year);

    @Query("SELECT t FROM Ticket t WHERE t.companyId=:companyId AND t.year=:year " +
            "AND (( t.startDate >= :startDate AND t.startDate < :endDate ) OR (t.endDate >= :startDate AND t.endDate < :endDate)) " +
            "ORDER BY t.startDate DESC ")
    List<Ticket> findBetweenHalfOpen(@Param("companyId") int companyId, @Param("year") int year,
                                     @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate );

    List<Ticket> findByCompanyIdAndYearOrderByCreatedOn(int companyId, int year);

}

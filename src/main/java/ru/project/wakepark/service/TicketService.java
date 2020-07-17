package ru.project.wakepark.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.repository.CrudTicketRepository;
import ru.project.wakepark.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.project.wakepark.util.TicketUtil.getInstance;

@Service
public class TicketService extends AbstractService<Ticket> {

    private final CrudTicketRepository repository;

    public TicketService(CrudTicketRepository repository) {
        super(repository, getInstance());
        this.repository = repository;
    }

    public List<Ticket> getAllEnable(int companyId) {
        return repository.getAllEnable(companyId);
    }

    public List<Ticket> getAllEnableInActiveTime(int companyId, LocalTime time) {
        return getInstance().getInActiveTime(getAllEnable(companyId), time);
    }

    public List<Ticket> getBetweenInclusive(int companyId, @Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        if (Objects.isNull(startDate) && Objects.isNull(endDate))
            return repository.getBetweenHalfOpen(companyId, null, null);

        return repository.getBetweenHalfOpen(companyId, DateTimeUtil.atStartOfDayOrMin(startDate), DateTimeUtil.atStartOfNextDayOrMax(endDate));
    }

    public List<Ticket> getWithFilter(int companyId,
                                      boolean active,
                                      boolean equipment,
                                      @Nullable LocalTime timeStart,
                                      @Nullable LocalTime timeEnd,
                                      @Nullable LocalDate dateStart,
                                      @Nullable LocalDate dateEnd) {

        return getInstance().getFiltered(
                getBetweenInclusive(companyId, dateStart, dateEnd),
                timeStart, timeEnd, active, equipment);
    }
}

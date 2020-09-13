package ru.project.wakepark.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.project.wakepark.model.ClientTicketStory;
import ru.project.wakepark.repository.commons.CommonDateRepository;

import java.util.List;

public interface JpaClientTicketStoryRepository extends CommonDateRepository<ClientTicketStory> {

    @Query("SELECT cts FROM ClientTicketStory cts WHERE cts.companyId=:company_id AND cts.clientTicket.id=:client_ticket_id")
    public List<ClientTicketStory> getAllByClientTicket(@Param("company_id") int companyId,
                                                        @Param("client_ticket_id") int clientTicketId);
}

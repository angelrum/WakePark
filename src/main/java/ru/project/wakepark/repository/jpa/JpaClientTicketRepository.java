package ru.project.wakepark.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.repository.commons.CommonDateRepository;

import java.util.List;

public interface JpaClientTicketRepository extends CommonDateRepository<ClientTicket> {

    @Query("SELECT ct FROM ClientTicket ct " +
            "JOIN FETCH ct.client " +
            "JOIN FETCH ct.ticket " +
            "WHERE ct.companyId=:company_id AND ct.client.id=:client_id")
    List<ClientTicket> findWithClientAndTicketByClient(@Param("company_id") int companyId,
                                                       @Param("client_id") int clientId);

    @Query("SELECT ct FROM ClientTicket ct " +
            "JOIN FETCH ct.client " +
            "JOIN FETCH ct.ticket " +
            "WHERE ct.companyId=:company_id AND ct.client.id=:client_id AND ct.active=true")
    List<ClientTicket> findWithClientAndTicketByClientAndActive(@Param("company_id") int companyId,
                                                       @Param("client_id") int clientId);

    @Query("SELECT ct FROM ClientTicket ct " +
            "JOIN FETCH ct.client " +
            "JOIN FETCH ct.ticket " +
            "WHERE ct.companyId=:company_id AND ct.client.id=:client_id AND ct.id=:id")
    List<ClientTicket> findByIdAndClient(@Param("company_id") int companyId,
                                         @Param("client_id") int clientId,
                                         @Param("id") int id);

    @Query("SELECT ct FROM ClientTicket ct " +
                "JOIN FETCH ct.client " +
                "JOIN FETCH ct.ticket " +
            "WHERE ct.companyId=:company_id AND ct.ticket.id=:ticket_id AND ct.client.id=:client_id AND ct.active = true " +
            "ORDER BY ct.createdOn")
    List<ClientTicket> findByClientAndTicket(@Param("company_id") int companyId,
                                             @Param("client_id") int clientId,
                                             @Param("ticket_id") int ticketId);
}

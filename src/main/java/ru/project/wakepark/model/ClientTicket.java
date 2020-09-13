package ru.project.wakepark.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "client_ticket")
public class ClientTicket extends AbstractDateChangedEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
//    @JoinColumns({
//            @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false),
//            @JoinColumn(name = "company_id", referencedColumnName = "company_id",insertable = false, updatable = false)
//    })
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    protected Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Ticket.class)
//    @JoinColumns({
//            @JoinColumn(name = "ticket_id", referencedColumnName = "id", updatable = false),
//            @JoinColumn(name = "company_id", referencedColumnName = "company_id", updatable = false)
//    })
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    protected Ticket ticket;

    @Column(name = "date_start")
    protected LocalDate dateStart;

    @Column(name = "date_end")
    protected LocalDate dateEnd;

    protected boolean active = true;

    public ClientTicket(Integer id, @NotNull Integer companyId, Client client, Ticket ticket, LocalDate dateStart, LocalDate dateEnd, boolean active,
                        @NotNull LocalDateTime createdOn, User createdBy, LocalDateTime changedOn, User changedBy) {
        super(id, companyId, createdOn, createdBy, changedOn, changedBy);
        this.client = client;
        this.ticket = ticket;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.active = active;
    }

    public ClientTicket() {
    }

    public ClientTicket(ClientTicket clt) {
        this(clt.id, clt.companyId, clt.client, clt.ticket, clt.dateStart, clt.dateEnd, clt.active, clt.createdOn, clt.createdBy, clt.changedOn, clt.changedBy);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ClientTicket{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", client=" + client.getTelnumber() +
                ", ticket=" + (Objects.nonNull(ticket.getName())? ticket.getName() : "") +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", active=" + active +
                ", changedOn=" + changedOn +
                //", changedBy=" + changedBy +
                ", createdOn=" + createdOn +
                //", createdBy=" + createdBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientTicket that = (ClientTicket) o;
        return  companyId.compareTo(that.companyId) == 0 &&
                id.compareTo(that.id)==0 &&
                active == that.active &&
                client.equals(that.client) &&
                ticket.equals(that.ticket) &&
                Objects.equals(dateStart, that.dateStart) &&
                Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, id, client, ticket, dateStart, dateEnd, active);
    }
}

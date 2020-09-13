package ru.project.wakepark.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "client_ticket_story")
public class ClientTicketStory extends AbstractDateChangedEntity {

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, targetEntity = ClientTicket.class)
    @JoinColumn(name = "client_ticket_id", referencedColumnName = "id")
    protected ClientTicket clientTicket;

    @Column
    protected LocalDate date;

    @Column(name = "time_start")
    protected LocalTime startTime;

    @Column(name = "time_end")
    protected LocalTime endTime;

    public ClientTicketStory(Integer id, Integer companyId, @NotNull ClientTicket clientTicket, LocalDate date,
                             LocalTime startTime, LocalTime endTime, @NotNull LocalDateTime createdOn, User createdBy, LocalDateTime changedOn, User changedBy) {
        super(id, companyId, createdOn, createdBy, changedOn, changedBy);
        this.clientTicket = clientTicket;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ClientTicketStory() {
    }

    public ClientTicketStory(ClientTicketStory story) {
        this(story.getId(), story.getCompanyId(), story.getClientTicket(), story.getDate(), story.getStartTime(), story.getEndTime(), story.getCreatedOn(), story.getCreatedBy(), story.getChangedOn(), story.getChangedBy());
    }

    public ClientTicket getClientTicket() {
        return clientTicket;
    }

    public void setClientTicket(ClientTicket clientTicket) {
        this.clientTicket = clientTicket;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}

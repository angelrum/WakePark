package ru.project.wakepark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.ClientTicketStory;
import ru.project.wakepark.repository.CrudClientTicketRepository;
import ru.project.wakepark.repository.CrudStoryRepository;
import ru.project.wakepark.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.project.wakepark.util.ValidationUtil.*;

@Service
public class StoryService extends AbstractService<ClientTicketStory> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private CrudStoryRepository repository;

    private CrudClientTicketRepository ctRepository;

    public StoryService(CrudStoryRepository repository, CrudClientTicketRepository ctRepository) {
        super(repository);
        this.repository = repository;
        this.ctRepository = ctRepository;
    }

    public List<ClientTicketStory> getAll(int companyId, int ctId) {
        log.info("Get all story for company {} with client ticket id {}", companyId, ctId);
        return repository.getAllByClientTicket(companyId, ctId);
    }

    public ClientTicketStory getOpenStory(int companyId, int ctId) {
        log.info("Get only open story for company {} and client ticket {}", companyId, ctId);
        return repository.getOpenStory(companyId, ctId);
    }

    public void setStoryCancellation(int companyId, ClientTicket ct, int userId) {
        log.info("Set story cancellation for company {} and client ticket {}", companyId, ct.getId());
        ClientTicketStory story = getOpenStory(companyId, ct.getId());
        if (Objects.nonNull(story)) {
            story.setEndTime(LocalTime.now());
        } else {
            story = getNewStory(companyId, ct);
            story.setEndTime(LocalTime.now());
        }
        repository.save(story, companyId, userId);
    }

    public void setStoryStart(int companyId, ClientTicket ct, int userId) {
        log.info("Create new story for company {} and client ticket {}", companyId, ct.getId());
        repository.save(getNewStory(companyId, ct), companyId, userId);
    }

    private ClientTicketStory getNewStory(int companyId, ClientTicket ct) {
        ClientTicketStory story = new ClientTicketStory();
        story.setClientTicket(ct);
        story.setDate(LocalDate.now());
        story.setStartTime(LocalTime.now());
        return story;
    }
}

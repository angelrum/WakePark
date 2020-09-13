package ru.project.wakepark.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.ClientTicketStory;
import ru.project.wakepark.repository.commons.AbstractBaseRepository;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.repository.jpa.JpaClientTicketStoryRepository;
import ru.project.wakepark.repository.jpa.JpaCompanyRepository;
import ru.project.wakepark.repository.jpa.JpaUserRepository;

import java.util.List;
import java.util.Objects;

@Repository
public class CrudStoryRepository extends AbstractDateChangedRepository<ClientTicketStory> {

    private JpaClientTicketStoryRepository repository;

    public CrudStoryRepository(JpaClientTicketStoryRepository repository,
                               JpaCompanyRepository companyRepository,
                               JpaUserRepository userRepository) {
        super(repository, companyRepository, userRepository);
        this.repository = repository;
    }

    public List<ClientTicketStory> getAllByClientTicket(int companyId, int ctId) {
        return repository.getAllByClientTicket(companyId, ctId);
    }

    public ClientTicketStory getOpenStory(int companyId, int ctId) {
        List<ClientTicketStory> storys = repository.getAllByClientTicket(companyId, ctId);
        return storys.stream()
                .filter(story -> Objects.isNull(story.getEndTime()))
                .findFirst().orElse(null);
    }

}

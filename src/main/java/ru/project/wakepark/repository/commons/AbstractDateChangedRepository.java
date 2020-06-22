package ru.project.wakepark.repository.commons;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import ru.project.wakepark.model.AbstractDateChangedEntity;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.model.Company;
import ru.project.wakepark.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoRepositoryBean
public abstract class AbstractDateChangedRepository <T extends AbstractDateChangedEntity> extends AbstractBaseRepository <T>{

    private CommonDateRepository<T> repository;

    private CommonBaseRepository<User> userRepository;

    public AbstractDateChangedRepository(CommonDateRepository<T> repository,
                                         CommonKeyRepository<Company> companyRepository,
                                         CommonBaseRepository<User> userRepository) {
        super(repository, companyRepository, userRepository);
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public T save(T t, int companyId, int changedBy) {
        if (!t.isNew()) {
            User chBy = userRepository.findOneByCompanyIdAndId(companyId, changedBy);
            if (Objects.isNull(chBy))
                return null;
            t.setChangedBy(chBy);
            t.setChangedOn(LocalDateTime.now());
        }
        return super.save(t, companyId, changedBy);
    }

    public List<T> getBeetwenTimeCreate(int companyId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.findByCompanyIdAndHalfCreatedOn(companyId, startDateTime, endDateTime);
    }

}

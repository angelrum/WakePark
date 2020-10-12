package ru.project.wakepark.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.wakepark.model.Company;
import ru.project.wakepark.model.User;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.repository.commons.CommonBaseRepository;
import ru.project.wakepark.repository.commons.CommonDateRepository;
import ru.project.wakepark.repository.commons.CommonKeyRepository;
import ru.project.wakepark.repository.jpa.JpaUserRepository;

@Repository
@Transactional(readOnly = true)
public class CrudUserRepository extends AbstractDateChangedRepository<User> {

    private JpaUserRepository repository;

    public CrudUserRepository(JpaUserRepository repository,
                              CommonKeyRepository<Company> companyRepository) {
        super(repository, companyRepository, repository);
        this.repository = repository;
    }

    public User getByLogin(String login) {
        return repository.findFirstByLogin(login);
    }
}

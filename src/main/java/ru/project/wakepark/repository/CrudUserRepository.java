package ru.project.wakepark.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.wakepark.model.Company;
import ru.project.wakepark.model.User;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.repository.commons.CommonBaseRepository;
import ru.project.wakepark.repository.commons.CommonDateRepository;
import ru.project.wakepark.repository.commons.CommonKeyRepository;

@Repository
@Transactional(readOnly = true)
public class CrudUserRepository extends AbstractDateChangedRepository<User> {

    public CrudUserRepository(CommonDateRepository<User> repository, CommonKeyRepository<Company> companyRepository, CommonBaseRepository<User> userRepository) {
        super(repository, companyRepository, userRepository);
    }

}

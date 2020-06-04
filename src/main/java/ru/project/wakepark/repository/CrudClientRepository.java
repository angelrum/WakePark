package ru.project.wakepark.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.repository.jpa.JpaClientRepository;
import ru.project.wakepark.repository.jpa.JpaCompanyRepository;
import ru.project.wakepark.repository.jpa.JpaUserRepository;

@Repository
@Transactional(readOnly = true)
public class CrudClientRepository extends AbstractDateChangedRepository<Client> {

    private JpaClientRepository repository;

    public CrudClientRepository(JpaClientRepository repository,
                                JpaCompanyRepository companyRepository,
                                JpaUserRepository userRepository) {
        super(repository, companyRepository, userRepository);
        this.repository = repository;
    }

    public Client getByPhone(int companyId, String phone) {
        return repository.findOneByCompanyIdAndTelnumber(companyId, phone);
    }

}

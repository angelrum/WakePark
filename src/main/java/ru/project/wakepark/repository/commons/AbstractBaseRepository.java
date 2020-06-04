package ru.project.wakepark.repository.commons;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import ru.project.wakepark.model.AbstractDateEntity;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.model.Company;
import ru.project.wakepark.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoRepositoryBean
public abstract class AbstractBaseRepository <T extends AbstractDateEntity> {

    private CommonBaseRepository<T> repository;

    private CommonKeyRepository<Company> companyRepository;

    private CommonBaseRepository<User> userRepository;

    public AbstractBaseRepository(CommonBaseRepository<T> repository, CommonKeyRepository<Company> companyRepository, CommonBaseRepository<User> userRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public T save(T t, int companyId, int createdBy) {
        if (!t.isNew() && repository.findOneByCompanyIdAndId(companyId, t.id()) == null)
            return null;

        Company company = companyRepository.getOne(companyId);
        User created = userRepository.findOneByCompanyIdAndId(companyId, createdBy);
        if (Objects.isNull(company)
                || Objects.isNull(created))
            return null;
        t.setCompanyId(companyId);
        if (t.isNew()) t.setCreatedBy(created);

        return repository.save(t);
    }

    @Transactional
    public boolean delete(int id, int companyId) {
        return repository.delete(id, companyId) !=0;
    }

    public List<T> getAll(int companyId) {
        return repository.findByCompanyIdOrderByCreatedOn(companyId);
    }

    public T get(int id, int companyId) {
        return repository.findOneByCompanyIdAndId(companyId, id);
    }

}

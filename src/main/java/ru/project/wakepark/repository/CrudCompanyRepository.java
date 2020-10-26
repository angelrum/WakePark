package ru.project.wakepark.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.wakepark.model.Company;
import ru.project.wakepark.repository.jpa.JpaCompanyRepository;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public class CrudCompanyRepository {

    private JpaCompanyRepository repository;

    public CrudCompanyRepository(JpaCompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> getAll() {
        return repository.findAll(Sort.by("name"));
    }

    public Company get(int id) {
        return repository.findById(id).orElse(null);
    }


    @Transactional
    public synchronized Company save(Company company) {
        if (!company.isNew() && get(company.id()) == null)
            return null;
        return repository.save(company);
    }

    @Transactional
    public synchronized boolean delete(int id) {
        return repository.delete(id)!=0;
    }

}

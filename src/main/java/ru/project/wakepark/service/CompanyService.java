package ru.project.wakepark.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.project.wakepark.model.Company;
import ru.project.wakepark.repository.CrudCompanyRepository;

import java.util.List;

import static ru.project.wakepark.util.ValidationUtil.*;

@Service
public class CompanyService {

    private final CrudCompanyRepository repository;

    public CompanyService(CrudCompanyRepository repository) {
        this.repository = repository;
    }

    public Company get(int id) {
        return checkNotFoundWithId(repository.get(id), id, 0);
    }

    public void delete(int id) {
        checknotfoundwithid(repository.delete(id), id, 0);
    }

    public Company create(Company company) {
        Assert.notNull(company, "company must not be null");
        return repository.save(company);
    }

    public Company update(Company company) {
        Assert.notNull(company, "company must not be null");
        return checkNotFoundWithId(repository.save(company), company.getId(), 0);
    }

    public List<Company> getAll() {
        return repository.getAll();
    }

}

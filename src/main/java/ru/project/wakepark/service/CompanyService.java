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
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Company create(Company company) {
        Assert.notNull(company, "company must not be null");
        return repository.save(company);
    }

    public Company update(Company company) {
        Assert.notNull(company, "company must not be null");
        return checkNotFoundWithId(repository.save(company), company.getId());
    }

    public List<Company> getAll() {
        return repository.getAll();
    }

}

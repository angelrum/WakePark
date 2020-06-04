package ru.project.wakepark.service;

import org.springframework.stereotype.Service;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.repository.CrudClientRepository;

@Service
public class ClientService extends AbstractService<Client> {

    private final CrudClientRepository repository;

    public ClientService(CrudClientRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Client getByPhone(String phone, int companyId) {
        return repository.getByPhone(companyId, phone);
    }

}

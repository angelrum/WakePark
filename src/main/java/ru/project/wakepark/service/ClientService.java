package ru.project.wakepark.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.repository.CrudClientRepository;
import ru.project.wakepark.util.ValidationUtil;

@Service
public class ClientService extends AbstractService<Client> {

    private final CrudClientRepository repository;

    public ClientService(CrudClientRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Client getByPhone(String phone, int companyId) {
        Assert.notNull(phone, "phone must not be null");
        return ValidationUtil.checkNotFound(repository.getByPhone(companyId, phone), "phone=" + phone);
    }

}

package ru.project.wakepark.service;

import org.springframework.stereotype.Service;
import ru.project.wakepark.repository.CrudUserRepository;

@Service
public class UserService extends AbstractService {

    private CrudUserRepository repository;

    public UserService(CrudUserRepository repository) {
        super(repository);
    }
}

package ru.project.wakepark.repository.jpa;

import ru.project.wakepark.model.Client;
import ru.project.wakepark.repository.commons.CommonDateRepository;
import ru.project.wakepark.repository.commons.CommonNamedRepository;


public interface JpaClientRepository extends CommonNamedRepository<Client>,
                                                CommonDateRepository<Client> {

}

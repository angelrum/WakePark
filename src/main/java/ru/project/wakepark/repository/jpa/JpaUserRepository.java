package ru.project.wakepark.repository.jpa;

import ru.project.wakepark.model.User;
import ru.project.wakepark.repository.commons.CommonBaseRepository;
import ru.project.wakepark.repository.commons.CommonDateRepository;
import ru.project.wakepark.repository.commons.CommonNamedRepository;

public interface JpaUserRepository extends CommonNamedRepository<User>, CommonDateRepository<User> {
}

package ru.project.wakepark.repository.commons;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.project.wakepark.model.AbstractKeyEntity;

import java.util.Optional;

@NoRepositoryBean
public interface CommonKeyRepository <T extends AbstractKeyEntity> extends JpaRepository<T, Integer> {


}

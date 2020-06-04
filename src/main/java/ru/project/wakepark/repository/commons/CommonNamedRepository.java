package ru.project.wakepark.repository.commons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.project.wakepark.model.AbstractNamedEntity;

@NoRepositoryBean
public interface CommonNamedRepository <T extends AbstractNamedEntity> extends JpaRepository<T, Integer> {

    T findOneByCompanyIdAndTelnumber(int companyId, String telnumber);

}

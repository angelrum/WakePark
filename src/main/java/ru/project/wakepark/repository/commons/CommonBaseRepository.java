package ru.project.wakepark.repository.commons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import ru.project.wakepark.model.AbstractBaseEntity;

import java.util.List;

@NoRepositoryBean
public interface CommonBaseRepository<T extends AbstractBaseEntity> extends JpaRepository <T, Integer> {

    List<T> findByCompanyIdOrderByCreatedOn(int companyId);

    T findOneByCompanyIdAndId(int companyId, int id);

    @Modifying
    @Query("DELETE FROM #{#entityName} t WHERE t.id=:id AND t.companyId=:companyId")
    int delete(@Param("id") int id, @Param("companyId") int companyId);

}

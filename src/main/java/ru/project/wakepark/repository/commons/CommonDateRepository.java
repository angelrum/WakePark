package ru.project.wakepark.repository.commons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import ru.project.wakepark.model.AbstractDateEntity;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface CommonDateRepository <T extends AbstractDateEntity> extends JpaRepository<T, Integer>, CommonBaseRepository<T> {

    @Query("SELECT ent FROM #{#entityName} ent " +
            "   WHERE ent.companyId=:companyId AND ent.createdBy.id=:createdBy " +
            "ORDER BY ent.createdOn")
    List<T> findByCompanyIdAndCreatedBy(@Param("companyId") int companyId, @Param("createdBy") int createdBy);

    @Query("SELECT ent FROM #{#entityName} ent " +
            "WHERE ent.companyId=:companyId AND ent.createdOn >= :startDateTime AND ent.createdOn < :endDateTime " +
            "ORDER BY ent.createdOn")
    List<T> findByCompanyIdAndHalfCreatedOn(@Param("companyId") int companyId,
                                            @Param("startDateTime") LocalDateTime start,
                                            @Param("endDateTime") LocalDateTime end);

}

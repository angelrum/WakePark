package ru.project.wakepark.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.project.wakepark.model.Company;
import ru.project.wakepark.repository.commons.CommonKeyRepository;

public interface JpaCompanyRepository extends CommonKeyRepository<Company> {

    @Modifying
    @Query("DELETE FROM Company c WHERE c.id=:id")
    int delete(@Param("id") int id);
}

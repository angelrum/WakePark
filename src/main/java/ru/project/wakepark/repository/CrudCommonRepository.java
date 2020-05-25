package ru.project.wakepark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CrudCommonRepository<T> extends JpaRepository <T, Integer> {

    List<T> getAllByCompanyId(int companyId);

    T getOneByCompanyIdAndId(int companyId, int id);

    List<T> getAllByCompanyIdAndTelnumber(int companyId, String telnumber);
}

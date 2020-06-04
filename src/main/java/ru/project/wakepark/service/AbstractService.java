package ru.project.wakepark.service;

import org.springframework.util.Assert;
import ru.project.wakepark.model.AbstractDateChangedEntity;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.project.wakepark.util.ValidationUtil.*;

public abstract class AbstractService <S extends AbstractDateChangedEntity> {

    private AbstractDateChangedRepository<S> repository;

    public AbstractService(AbstractDateChangedRepository<S> repository) {
        this.repository = repository;
    }

    public S get(int id, int companyId) {
        return checkNotFoundWithId(repository.get(id, companyId), id);
    }

    public void delete(int id, int companyId) {
        checkNotFoundWithId(repository.delete(id, companyId), id);
    }

    public S create(S s, int companyId, int userId) {
        checkNotNull(s);
        return repository.save(s, companyId, userId);
    }

    public S update(S s, int companyId, int userId) {
        checkNotNull(s);
        return checkNotFoundWithId(repository.save(s, companyId, userId), s.id());
    }

    public List<S> getAll(int companyId) {
        return repository.getAll(companyId);
    }

    private void checkNotNull(S s) {
        Assert.notNull(s, String.format("%s, must not be null", s.getClass().getName()));
    }

    public List<S> getBeetwenTimeCreated(int companyId, LocalDate start, LocalDate end) {
        return repository.getBeetwenTimeCreate(companyId, DateTimeUtil.atStartOfDayOrMin(start), DateTimeUtil.atStartOfNextDayOrMax(end));
    }

}

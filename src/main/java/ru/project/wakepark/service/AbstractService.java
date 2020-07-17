package ru.project.wakepark.service;

import ru.project.wakepark.model.AbstractDateChangedEntity;
import ru.project.wakepark.repository.commons.AbstractDateChangedRepository;
import ru.project.wakepark.util.DateTimeUtil;
import ru.project.wakepark.util.ValidationInputData;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.project.wakepark.util.ValidationUtil.*;

public abstract class AbstractService <S extends AbstractDateChangedEntity> {

    private AbstractDateChangedRepository<S> repository;

    private ValidationInputData<S> util;

    public AbstractService(AbstractDateChangedRepository<S> repository, ValidationInputData<S> util) {
        this.repository = repository;
        this.util = util;
    }

    public AbstractService(AbstractDateChangedRepository<S> repository) {
        this.repository = repository;
    }

    public S get(int id, int companyId) {
        return checkNotFoundWithId(repository.get(id, companyId), id, companyId);
    }

    public void delete(int id, int companyId) {
        checknotfoundwithid(repository.delete(id, companyId), id, companyId);
    }

    public S create(S s, int companyId, int userId) {
        checkNotNull(s);
        checkNew(s);
        if (Objects.nonNull(util)) util.checkInputData(s);
        return repository.save(s, companyId, userId);
    }

    public S update(S s, int companyId, int userId) {
        checkNotNull(s);
        if (Objects.nonNull(util)) util.checkInputData(s);
        return checkNotFoundWithId(repository.save(s, companyId, userId), s.id(), companyId);
    }

    public List<S> getAll(int companyId) {
        return repository.getAll(companyId);
    }

    public List<S> getBeetwenTimeCreated(int companyId, LocalDate start, LocalDate end) {
        return repository.getBeetwenTimeCreate(companyId, DateTimeUtil.atStartOfDayTimeOrMin(start), DateTimeUtil.atStartOfNextDayTimeOrMax(end));
    }

}

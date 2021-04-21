package ru.project.wakepark.web.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.View;
import ru.project.wakepark.model.AbstractDateChangedEntity;
import ru.project.wakepark.service.AbstractService;
import ru.project.wakepark.to.BaseTo;
import ru.project.wakepark.util.AbstractUtil;

import java.util.List;

import static ru.project.wakepark.web.SecurityUtil.*;

public abstract class AbstractUIController <S extends AbstractDateChangedEntity, To extends BaseTo> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private AbstractService<S> service;

    private AbstractUtil<S, To> util;

    public AbstractUIController(AbstractService<S> service, AbstractUtil<S, To> util) {
        this.service = service;
        this.util = util;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<To> getAll() {
        log.debug("Get all method in class {}", getClass());
        log.info("user {} getAll for company id {} from {}", authUserId(), authCompanyId(), this.getClass().getSimpleName());
        return util.getTos(service.getAll(authCompanyId()));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Validated(View.Web.class) S s) {
        log.info("user {} create or update {} from {}", authUserId(), s.toString(), this.getClass().getSimpleName());
        if (s.isNew())
            service.create(s, authCompanyId(), authUserId());
        else
            service.update(s, authCompanyId(), authUserId());
    }

    @GetMapping(value = "/{id}")
    public BaseTo get(@PathVariable Integer id) {
        log.info("user {} get by id {} and company {} from {}",authUserId(), id, authCompanyId(), this.getClass().getSimpleName());
        return util.createTo(service.get(id, authCompanyId()));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) {
        log.info("user {} delete by id {} from {}", authUserId(), id, this.getClass().getSimpleName());
        service.delete(id, authCompanyId());
    }
}

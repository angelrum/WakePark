package ru.project.wakepark.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.View;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.service.ClientService;
import ru.project.wakepark.to.ClientTo;
import static ru.project.wakepark.util.ClientUtil.getInstance;


import java.util.List;

@RestController
@RequestMapping("/ajax/controller/clients")
public class ClientsUIController {

    @Autowired
    public ClientService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientTo> getAll() {
        return getInstance().getTos(service.getAll(AuthorizedUser.getCompanyId()));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Validated(View.Web.class) Client client) {
        getInstance().checkPhone(client);
        if (client.isNew())
            service.create(client, AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
        else
            service.update(client, AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
    }

    @GetMapping(value = "/{id}")
    public ClientTo get(@PathVariable Integer id) {
        return getInstance().createTo(service.get(id, AuthorizedUser.getCompanyId()));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id, AuthorizedUser.getCompanyId());
    }

}

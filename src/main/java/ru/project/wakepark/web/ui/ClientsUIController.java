package ru.project.wakepark.web.ui;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.project.wakepark.AuthorizedUser;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.service.ClientService;
import ru.project.wakepark.to.ClientTo;
import ru.project.wakepark.util.ClientUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/ajax/controller/clients")
public class ClientsUIController extends AbstractUIController<Client, ClientTo> {

    private ClientService service;

    public ClientsUIController(ClientService service) {
        super(service, ClientUtil.getInstance());
        this.service = service;
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientTo getByTelnumber(@RequestParam String telnumber) {
        log.info("get by phone {}", telnumber);
        return ClientUtil.getInstance().createTo(service.getByPhone(telnumber, AuthorizedUser.getCompanyId()));
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<ClientTo> getAll() {
//        return getInstance().getTos(service.getAll(AuthorizedUser.getCompanyId()));
//    }
//
//    @PostMapping
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void createOrUpdate(@Validated(View.Web.class) Client client) {
//        getInstance().checkInData(client);
//        if (client.isNew())
//            service.create(client, AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
//        else
//            service.update(client, AuthorizedUser.getCompanyId(), AuthorizedUser.getId());
//    }
//
//    @GetMapping(value = "/{id}")
//    public ClientTo get(@PathVariable Integer id) {
//        return getInstance().createTo(service.get(id, AuthorizedUser.getCompanyId()));
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public void delete(@PathVariable int id) {
//        service.delete(id, AuthorizedUser.getCompanyId());
//    }

}

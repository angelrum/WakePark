package ru.project.wakepark.util;

import ru.project.wakepark.model.Client;
import ru.project.wakepark.to.ClientTo;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientUtil extends AbstractUtil<Client, ClientTo> implements ValidationInputData<Client> {

    private static ClientUtil instance;

    private ClientUtil() {
    }

    public static ClientUtil getInstance() {
        if (Objects.isNull(instance))
            instance = new ClientUtil();
        return instance;
    }

    public ClientTo createTo(Client client) {
        return new ClientTo(client.getId(), client.getFirstname(), client.getLastname(),
                client.getMiddlename(), client.getTelnumber(), client.getCity(), client.getEmail());
    }

    public static List<String> getTableName() {
        return List.of(
                "Имя",
                "Фамилия",
                "Отчество",
                "Номер телефона",
                "Город",
                "Email");
    }

    @Override
    public void checkInputData(Client cl) {
        if (!cl.getTelnumber().startsWith("+7")) {
            cl.setTelnumber("+7".concat(cl.getTelnumber()));
        }
    }
}

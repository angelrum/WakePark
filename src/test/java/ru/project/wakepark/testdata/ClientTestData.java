package ru.project.wakepark.testdata;

import org.assertj.core.util.Strings;
import ru.project.wakepark.TestMatcher;
import ru.project.wakepark.model.AbstractDateEntity;
import ru.project.wakepark.model.Client;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientTestData implements TestData<Client> {

    public static TestMatcher<Client> CLIENT_MATCHER = TestMatcher.usingFieldsComparator(Client.class, "createdBy", "changedBy");

    public static int CLIENT_ID1 = 10_005;

    public static int CLIENT_ID2 = 10_006;

    public static int CLIENT_ID3 = 10_007;

    public static String NOT_FOUND_PHONE = "+7(912)111-11-18";

    public static Client CLIENT1 = new Client(CLIENT_ID1, CompanyTestData.WAKE_ID1, "Олег", "Иванов", "Иванович", "+7(911)111-11-16", "Анапа", LocalDateTime.of(2020, Month.JUNE, 2, 11, 0, 0), UserTestData.USER3, null, null);

    public static Client CLIENT2 = new Client(CLIENT_ID2, CompanyTestData.WAKE_ID1, "Александр", "Иванов", "Иванович", "+7(911)111-11-17", "Новороссийск", LocalDateTime.of(2020, Month.JUNE, 2, 10, 0, 0), UserTestData.USER3, null, null);

    public static Client CLIENT3 = new Client(CLIENT_ID3, CompanyTestData.WAKE_ID1, "Сергей", "Иванов", "Иванович", "+7(911)111-11-18", "Новороссийск", LocalDateTime.of(2020, Month.JUNE, 2, 12, 0, 0), UserTestData.USER3, null, null);

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>(List.of(CLIENT1, CLIENT2, CLIENT3));
        clients.sort(Comparator.comparing(Client::getCreatedOn));
        return clients;
    }

    @Override
    public int getIdDelete() {
        return CLIENT_ID2;
    }

    @Override
    public List<Client> getAllWithoutDelete() {
        return List.of(CLIENT1, CLIENT3);
    }

    @Override
    public Client getOne() {
        return CLIENT1;
    }

    @Override
    public Client getNew() {
        Client cl = new Client(CLIENT1);
        cl.setId(null);
        cl.setFirstname("Федор");
        cl.setCity("Краснодар");
        cl.setTelnumber("+7(911)111-11-19");
        return cl;
    }

    @Override
    public Client getUpdate() {
        Client upd = new Client(CLIENT1);
        upd.setCity("Краснодар");
        return upd;
    }

    @Override
    public int getId() {
        return CLIENT_ID1;
    }

    @Override
    public int getCreatedId() {
        return UserTestData.USER_ID1;
    }

}

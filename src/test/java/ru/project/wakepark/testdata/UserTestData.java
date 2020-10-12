package ru.project.wakepark.testdata;

import ru.project.wakepark.TestMatcher;
import ru.project.wakepark.model.Role;
import ru.project.wakepark.model.User;
import ru.project.wakepark.testdata.CompanyTestData;
import ru.project.wakepark.testdata.TestData;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserTestData implements TestData<User> {

    public static TestMatcher USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "createdBy", "changedBy");

    public static final int USER_ID1 =  10_002;
    public static final int USER_ID2 =  10_003;
    public static final int USER_ID3 =  10_004;

    public static final User USER1 = new User(USER_ID1, CompanyTestData.WAKE_ID1, null, "test1", "12345", "Ivan", "Ivanov", "Ivanovich", "+7(911)111-11-13", "test1@test.ru", true,
            LocalDateTime.of(2020, Month.JUNE, 2, 10, 0, 0), null, null, null, Role.ADMIN);

    public static final User USER2 = new User(USER_ID2, CompanyTestData.WAKE_ID1, null, "test2", "123456", "Vova", "Ivanov", "Ivanovich", "+7(911)111-11-14", "test2@test.ru", true,
            LocalDateTime.of(2020, Month.JUNE, 2, 10, 0, 0), USER1, null, null, Role.MANAGER, Role.USER);

    public static final User USER3 = new User(USER_ID3, CompanyTestData.WAKE_ID1, null, "test3", "1234567", "Yulia", "Ivanova", "Ivanovna", "+7(911)111-11-15", "test3@test.ru", true,
            LocalDateTime.of(2020, Month.JUNE, 2, 10, 0, 0), USER1, LocalDateTime.of(2020, Month.JUNE, 2, 12, 0, 0), USER1, Role.USER);

    public static final ArrayList<User> USERS = new ArrayList(List.of(USER1, USER2, USER3));

    @Override
    public User getNew() {
        return new User(null, CompanyTestData.WAKE_ID1, null, "testNew", "pass", "Anton", "Ivanov", "Ivanovich", "+7(911)111-11-16", "test4@test.ru", true,
                LocalDateTime.of(2020, Month.JUNE, 2, 10, 0, 0), USER1, null, null, Role.USER);
    }

    public User getUpdate() {
        User user = new User(USER2);
        user.setEmail("upd@test.ru");
        user.setRoles(Set.of(Role.MANAGER));
        return user;
    }

    @Override
    public List<User> getAll() {
        return USERS;
    }

    @Override
    public User getOne() {
        return USER1;
    }

    @Override
    public int getId() {
        return USER_ID1;
    }

    @Override
    public int getCreatedId() {
        return USER_ID1;
    }

    @Override
    public int getIdDelete() {
        return USER_ID2;
    }

    @Override
    public List<User> getAllWithoutDelete() {
        return List.of(USER1, USER3);
    }
}

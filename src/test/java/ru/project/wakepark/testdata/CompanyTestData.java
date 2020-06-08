package ru.project.wakepark.testdata;

import ru.project.wakepark.TestMatcher;
import ru.project.wakepark.model.Company;

import java.util.List;

public class CompanyTestData {

    public static TestMatcher<Company> COMPANY_MATCHER = TestMatcher.usingFieldsComparator(Company.class);

    public static final int WAKE_ID1 = 10_000;
    public static final int WAKE_ID2 = 10_001;
    public static final int NOT_FOUND_ID = 10_000_000;

    public static final Company WAKE_PARK1 = new Company(WAKE_ID1, "WakePark1", "ООО ТестКомпания1", "+7(911)111-11-11");
    public static final Company WAKE_PARK2 = new Company(WAKE_ID2, "WakePark2", "ООО ТестКомпания2", "+7(921)111-11-11");

    public static List<Company> COMPANYS = List.of(WAKE_PARK1, WAKE_PARK2);

    public static Company getNew() {
        return new Company(null, "WakePark3", "ООО ТестКомпания3", "+7(931)111-11-11");
    }

    public static Company getUpdate() {
        return new Company(WAKE_ID1, "WakePark Update", WAKE_PARK1.getContactName(), WAKE_PARK1.getContactPhone());
    }
}

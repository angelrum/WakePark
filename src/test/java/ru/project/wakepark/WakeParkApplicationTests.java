package ru.project.wakepark;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@SpringBootTest
class WakeParkApplicationTests {

    @Autowired
    ReloadableResourceBundleMessageSource messageSource;

    @Test
    void contextLoads() {
        System.out.println("====== Check bundle ======");
        System.out.println(String.format("file:%s/config/messages/app", System.getenv("WAKEPARK_ROOT")));
        String test = messageSource.getMessage("userTo.name", null, "Default", new Locale("ru"));
        System.out.println(test);
    }

}

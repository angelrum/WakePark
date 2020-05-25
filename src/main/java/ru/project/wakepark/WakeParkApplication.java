package ru.project.wakepark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.project.wakepark.repository.CrudClientCommonRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.project.wakepark.**.repository"})
public class WakeParkApplication {
    private static final Logger log = LoggerFactory.getLogger(WakeParkApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WakeParkApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo (CrudClientCommonRepository repository) {
        log.info("Start query exist");
        return args -> {
            repository.getAllByCompanyId(10000).forEach(System.out::println);
        };
    }

}

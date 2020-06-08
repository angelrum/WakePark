package ru.project.wakepark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.project.wakepark.repository.CrudClientRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.project.wakepark.**.repository"})
public class WakeParkApplication extends SpringBootServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(WakeParkApplication.class);

    // для запуска из Tomcat
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WakeParkApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WakeParkApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo (CrudClientRepository clientRepository) {
////        log.info("Start query exist");
////        return args -> {
////            System.out.println("====== Get clients ======");
////            clientRepository.getAll(10000).forEach(System.out::println);
////            clientRepository.delete(10004, 10005);
////            clientRepository.getAll(10000).forEach(System.out::println);
////        };
//        return null;
//    }

    @Bean
    @Profile("test")
    public CacheManager getNoOpCacheManager() {
        return new NoOpCacheManager();
    }

}

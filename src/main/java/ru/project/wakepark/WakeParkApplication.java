package ru.project.wakepark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.core.MessageSendingOperations;
import ru.project.wakepark.event.EventManager;
import ru.project.wakepark.event.MailingOfQueue;
import ru.project.wakepark.event.MailingOfStateQueue;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.service.QueueService;
import ru.project.wakepark.service.QueueStateService;
import ru.project.wakepark.web.converter.DateTimeFormatters;
import ru.project.wakepark.web.converter.StringToPassEnumConverter;
import ru.project.wakepark.web.json.JacksonObjectMapper;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.project.wakepark.**.repository"})
public class WakeParkApplication extends SpringBootServletInitializer {
    private static final String DEF_ENCODING = "UTF-8";
    private static final Logger log = LoggerFactory.getLogger(WakeParkApplication.class);

    // для запуска из Tomcat
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WakeParkApplication.class);
    }

//    public static void main(String[] args) {
//        SpringApplication.run(WakeParkApplication.class, args);
//    }

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

//    @Bean
//    @Profile("test")
//    public CacheManager getNoOpCacheManager() {
//        return new NoOpCacheManager();
//    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(JacksonObjectMapper.getMapper());
        return converter;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(List.of(
                new MediaType(MediaType.TEXT_PLAIN, Charset.forName(DEF_ENCODING)),
                new MediaType(MediaType.TEXT_HTML, Charset.forName(DEF_ENCODING))));
        return messageConverter;
    }

    @Autowired
    public void addFormaters(FormatterRegistry registry) {
        registry.addConverter(new StringToPassEnumConverter());
        registry.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        registry.addFormatter(new DateTimeFormatters.LocalTimeFormatter());
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource () {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(String.format("file:%s/config/messages/app", System.getenv("WAKEPARK_ROOT")));
        messageSource.setDefaultEncoding(DEF_ENCODING);
        messageSource.setCacheSeconds(5);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public MessageSourceAccessor getMessageSourceAccessor(ReloadableResourceBundleMessageSource messageSource) {
        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource);
        return messageSourceAccessor;
    }

    @Bean(name = "activeQueue")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Map<Integer, LinkedList<Set<ClientTicket>>> getActiveQueue() {
        Map<Integer, LinkedList<Set<ClientTicket>>> activeQueue = new HashMap<>();
        return activeQueue;
    }

    @Bean(name = "stoppedQueue")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Map<Integer, LinkedList<Set<ClientTicket>>> getStoppedQueue() {
        Map<Integer, LinkedList<Set<ClientTicket>>> stoppedQueue = new HashMap<>();
        return stoppedQueue;
    }

    @Bean
    public EventManager getEventManager() {
        EventManager em = new EventManager(); //снова циркуляция
        //em.subscribe("queue", new MailingOfQueue(message, service));
        //em.subscribe("state", new MailingOfStateQueue(message, stateService));
        return em;
    }



}

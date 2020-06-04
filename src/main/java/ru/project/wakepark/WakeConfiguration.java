package ru.project.wakepark;

import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class WakeConfiguration {
    @Bean
    @Profile("test")
    public CacheManager getNoOpCacheManager() {
        return new NoOpCacheManager();
    }
}

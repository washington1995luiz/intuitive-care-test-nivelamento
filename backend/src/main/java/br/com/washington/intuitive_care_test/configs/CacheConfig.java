package br.com.washington.intuitive_care_test.configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        String[] cacheNames = {
                "health-plan-operators-id",
                "health-plan-operators-all",
                "health-plan-operators-top10Operators-latest-year",
                "health-plan-operators-top10Operators-latest-quarter",
                "financial-statements-id",
                "financial-statements-all"
        };
        return new ConcurrentMapCacheManager(cacheNames);
    }
}

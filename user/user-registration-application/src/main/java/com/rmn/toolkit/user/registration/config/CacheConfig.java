package com.rmn.toolkit.user.registration.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CacheConfig {
    private static final String JWT_CLAIMS_CACHE_NAME = "jwtClaims";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<>();
        caches.add(new ConcurrentMapCache(JWT_CLAIMS_CACHE_NAME));
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}

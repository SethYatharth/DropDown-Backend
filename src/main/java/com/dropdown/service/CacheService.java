package com.dropdown.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {

    private final CacheManager cacheManager;
    private final Set<String> cacheKeys = ConcurrentHashMap.newKeySet(); // Track keys manually

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @CachePut(value = "myCache", key = "#key")
    public LocalDateTime put(String key, LocalDateTime value) {
        cacheKeys.add(key); // Track key when added
        return value;
    }

    @Cacheable(value = "myCache", key = "#key")
    public LocalDateTime get(String key) {
        return null;
    }

    @CacheEvict(value = "myCache", key = "#key")
    public void remove(String key) {
        cacheKeys.remove(key);
    }

    @CacheEvict(value = "myCache", allEntries = true)
    public void clear() {
        cacheKeys.clear();
    }

    // Scheduled task to remove expired cache entries every minute
    @Scheduled(fixedRate = 60000) // Runs every 1 minute
    public void evictExpiredEntries() {
        Cache cache = cacheManager.getCache("myCache");
        if (cache != null) {
            for (String key : cacheKeys) {
                LocalDateTime cachedValue = cache.get(key, LocalDateTime.class);
                if (cachedValue != null && cachedValue.isBefore(LocalDateTime.now().minusMinutes(10))) {
                    remove(key); // Remove from cache
                }
            }
        }
    }

}

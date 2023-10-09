package com.xirui.cache.bean;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xirui.cache.CacheEnum;
import com.xirui.cache.service.AbstractCache;

import java.io.Serializable;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 * @author zcj
 */
public class LocalCache extends AbstractCache {
    Cache<String, Serializable> caches = Caffeine.newBuilder().maximumSize(1000).expireAfterWrite(Duration.ofMinutes(5)).expireAfterAccess(Duration.ofMinutes(1)).build();
    public LocalCache(CacheEnum.Type type) {
        super(type);
    }

    @Override
    public Serializable get(String key) {
        return caches.getIfPresent(key);
    }
    @Override
    public void put(String key, Serializable serializable) {
        caches.put(key,serializable);
    }

    @Override
    public void evict(String key) {
        caches.invalidate(key);
    }

}

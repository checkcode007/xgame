package com.proto.cache.bean.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.proto.cache.CacheEnum;
import com.proto.cache.bean.AbstractCache;

import java.io.Serializable;
import java.time.Duration;

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

package com.gamefull.cache.bean;

import com.gamefull.cache.CacheEnum;
import com.gamefull.cache.bean.local.LocalCache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractCaches implements ICaches {
    private Map<String , BaseCache> map = new ConcurrentHashMap<>();
    private CacheEnum.Type type;

    public AbstractCaches(CacheEnum.Type type) {
        this.type = type;
    }

    public Serializable get(String key, String cacheKey) {
        BaseCache caches = map.get(key);
        if(caches == null){
            return null;
        }
        return caches.get(cacheKey);
    }
    public void put(String key,String cacheKey, Serializable serializable) {
        BaseCache caches = map.get(key);
        if(caches == null){
            caches = new LocalCache(CacheEnum.Type.LOCAL);
            map.put(key,caches);
        }
        caches.put(cacheKey,serializable);
    }

    public void evict(String key,String cacheKey) {
        BaseCache caches = map.get(key);
        if(caches == null){
            return;
        }
        caches.evict(cacheKey);
    }

}

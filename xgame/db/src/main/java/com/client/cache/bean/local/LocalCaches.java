package com.client.cache.bean.local;

import com.client.cache.CacheEnum;
import com.client.cache.bean.AbstractCaches;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 * @author zcj
 */
public class LocalCaches extends AbstractCaches {

    private Map<String , LocalCache> map = new ConcurrentHashMap<>();

    public LocalCaches(CacheEnum.Type type) {
        super(type);
    }

    @Override
    public Serializable get(String key,String cacheKey) {
        LocalCache caches = map.get(key);
        if(caches == null){
            return null;
        }
        return caches.get(cacheKey);
    }
    @Override
    public void put(String key,String cacheKey, Serializable serializable) {
        LocalCache caches = map.get(key);
        if(caches == null){
            caches = new LocalCache(CacheEnum.Type.LOCAL);
            map.put(key,caches);
        }
        caches.put(cacheKey,serializable);
    }
    @Override
    public void evict(String key,String cacheKey) {
        LocalCache caches = map.get(key);
        if(caches == null){
            return;
        }
        caches.evict(cacheKey);
    }

}

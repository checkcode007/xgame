package com.proto.cache.bean.remote;

import com.proto.cache.CacheEnum;
import com.proto.cache.bean.AbstractCaches;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 * @author zcj
 */
public class RedisCaches extends AbstractCaches {

    private Map<String , RedisCache> map = new ConcurrentHashMap<>();

    public RedisCaches(CacheEnum.Type type) {
        super(type);
    }

    @Override
    public Serializable get(String key,String cacheKey) {
        RedisCache caches = map.get(key);
        if(caches == null){
            return null;
        }
        return caches.get(cacheKey);
    }
    @Override
    public void put(String key,String cacheKey, Serializable serializable) {
        RedisCache caches = map.get(key);
        if(caches == null){
            caches = new RedisCache(CacheEnum.Type.REMOTE);
            map.put(key,caches);
        }
        caches.put(cacheKey,serializable);
    }
    @Override
    public void evict(String key,String cacheKey) {
        RedisCache caches = map.get(key);
        if(caches == null){
            return;
        }
        caches.evict(cacheKey);
    }

}

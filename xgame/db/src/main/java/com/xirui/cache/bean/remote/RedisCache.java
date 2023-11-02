//package com.xirui.cache.bean.remote;
//
//import com.xirui.cache.CacheEnum;
//import com.xirui.cache.bean.AbstractCache;
//import com.xirui.redis.RedisUtil;
//
//import java.io.Serializable;
//
//public class RedisCache extends AbstractCache {
//    RedisUtil redisUtil = new RedisUtil();
//
//
//
//    public RedisCache(CacheEnum.Type type) {
//        super(type);
//    }
//    @Override
//    public Serializable get(String key) {
//        return caches.getIfPresent(key);
//    }
//    @Override
//    public void put(String key, Serializable serializable) {
//        caches.put(key,serializable);
//    }
//
//    @Override
//    public void evict(String key) {
//        caches.invalidate(key);
//    }
//}

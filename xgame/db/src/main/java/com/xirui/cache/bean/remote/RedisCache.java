package com.xirui.cache.bean.remote;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xirui.Managers;
import com.xirui.cache.CacheEnum;
import com.xirui.cache.bean.AbstractCache;
import com.xirui.redis.RedisUtil;
import com.xirui.redis.redisson.RedissonMgr;

import java.io.Serializable;

public class RedisCache extends AbstractCache {
    RedissonMgr redissonMgr;
    public RedisCache(CacheEnum.Type type) {
        super(type);
        redissonMgr = Managers.ins.getRedissonMgr();
    }
    @Override
    public Serializable get(String key) {
        return redissonMgr.get(key);
    }
    @Override
    public void put(String key, Serializable serializable) {
        redissonMgr.set(key,serializable);
    }

    @Override
    public void evict(String key) {
        redissonMgr.delete(key);
    }
}

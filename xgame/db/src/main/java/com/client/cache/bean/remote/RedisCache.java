package com.client.cache.bean.remote;

import com.client.Managers;
import com.client.cache.CacheEnum;
import com.client.cache.bean.AbstractCache;
import com.client.redis.redisson.RedissonMgr;

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

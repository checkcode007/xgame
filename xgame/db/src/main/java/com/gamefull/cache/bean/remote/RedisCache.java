package com.gamefull.cache.bean.remote;

import com.gamefull.Managers;
import com.gamefull.cache.CacheEnum;
import com.gamefull.cache.bean.AbstractCache;
import com.gamefull.redis.redisson.RedissonMgr;

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

package com.proto.cache.bean.remote;

import com.proto.Managers;
import com.proto.cache.CacheEnum;
import com.proto.cache.bean.AbstractCache;
import com.proto.redis.redisson.RedissonMgr;

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

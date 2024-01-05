package com.xirui.redis.redisson;

import org.redisson.api.*;

import java.io.Serializable;

public class RedissonMgr extends BaseRedissonMgr{
    public void set(String key, Object value) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    public Serializable get(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        return (Serializable)bucket.get();
    }
    public boolean delete(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        return bucket.delete();
    }
}

package com.client;

import com.client.redis.redisson.RedissonMgr;

public enum Managers {
    ins;
    private RedissonMgr redissonMgr;

    Managers() {
    }

    public void init(){
        redissonMgr =new RedissonMgr();
    }

    public RedissonMgr getRedissonMgr() {
        return redissonMgr;
    }
}

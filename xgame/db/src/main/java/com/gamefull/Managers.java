package com.gamefull;

import com.gamefull.redis.redisson.RedissonMgr;

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

package com.client.cache.bean;

import com.client.cache.CacheEnum;

import java.io.Serializable;

public abstract class AbstractCache implements BaseCache{

    public CacheEnum.Type type;

    public AbstractCache(CacheEnum.Type type) {
        this.type = type;
    }

    @Override
    public Serializable get(String key) {
        return null;
    }

    @Override
    public void put(String key, Serializable serializable) {

    }

    @Override
    public void evict(String key) {

    }
}

package com.client.cache.bean;


import java.io.Serializable;

public interface BaseCache<T extends Serializable > {
    T get(String key);
    void put(String key,T t);
    void evict(String key);

}

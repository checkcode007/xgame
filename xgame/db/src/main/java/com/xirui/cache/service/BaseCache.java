package com.xirui.cache.service;


import java.io.Serializable;

public interface BaseCache<T extends Serializable > {
    T get(String key);
    void put(String key,T t);
    void evict(String key);

}

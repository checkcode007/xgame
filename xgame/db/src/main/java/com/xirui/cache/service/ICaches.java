package com.xirui.cache.service;

import java.io.Serializable;

/**
 * 本地缓存
 * @author zcj
 */
public interface ICaches {
    Serializable get(String key,String cacheKey);
    void put(String key,String cacheKey,Serializable serializable);

    void evict(String key,String cacheKey);
}

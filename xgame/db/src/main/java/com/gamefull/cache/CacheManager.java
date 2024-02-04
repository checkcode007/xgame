package com.gamefull.cache;


import com.gamefull.cache.bean.ICaches;
import com.gamefull.cache.bean.local.LocalCaches;
import com.gamefull.cache.bean.remote.RedisCaches;

import java.util.Map;


public enum CacheManager {
    ins;
    private Map<CacheEnum.Type, ICaches> map = null;
    CacheManager() {
        init();
    }

    public void init(){
        map = Map.of(CacheEnum.Type.LOCAL,new LocalCaches(CacheEnum.Type.LOCAL)
        ,CacheEnum.Type.REMOTE,new RedisCaches(CacheEnum.Type.REMOTE));
    }
    public ICaches get(CacheEnum.Type type){
        return map.get(type);
    }

}

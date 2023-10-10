package com.xirui.cache;


import com.xirui.cache.service.ICaches;
import com.xirui.cache.service.LocalCaches;

import java.util.Map;

public enum CacheManager {
    ins;
    private Map<CacheEnum.Type, ICaches> map = null;
    CacheManager() {
        init();
    }

    public void init(){
        map = Map.of(CacheEnum.Type.LOCAL,new LocalCaches(CacheEnum.Type.LOCAL));
    }
    public ICaches get(CacheEnum.Type type){
        return map.get(type);
    }

}

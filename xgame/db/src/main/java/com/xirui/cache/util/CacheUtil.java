package com.xirui.cache.util;

import java.lang.reflect.Field;

/**
 * 缓存工具类
 * @author zcj
 */
public class CacheUtil {

    public static String createKey(String key1,String key2,Object[] args) throws Exception{
        String redisK;
        if(!key2.contains("#") ){
            redisK = key1+":"+key2;
            return redisK;
        }
        String[] kk = key2.split("#");
        redisK =key1+":"+kk[0];
        if(kk.length ==2){
            String param = kk[1];
            String p0 =param;
            String p1 =null;
            if(param.contains(".")){
                String[] pp = param.split("\\.");
                if(pp.length ==2){
                    p0 = pp[0];
                    p1 = pp[1];
                }
            }
            Object arg1 = args[0];
            if(p1!=null){
                Field field = arg1.getClass().getDeclaredField(p1);
                if(field!=null){
                    field.setAccessible(true);
                    redisK+=field.get(arg1);
                }
            }else{
                redisK+=arg1;
            }

        }
        return redisK;
    }

}

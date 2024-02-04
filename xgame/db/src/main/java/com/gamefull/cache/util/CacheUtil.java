package com.gamefull.cache.util;

import java.lang.reflect.Field;

/**
 * 缓存工具类
 * @author zcj
 */
public class CacheUtil {

    public static String createKey(String group,String name,String[] param,Object[] args) throws Exception{
        String ret = group+":"+name;
        if(param == null || param.length<1){
            return  ret;
        }
        int index=0;
        for (String s : param) {
            Object arg1 = args[index++];
            if(!s.contains(".")){
                ret+=":"+arg1;
                continue;
            }
            String[] pp = s.split("\\.");
            if(pp.length !=2) continue;
            String p1 = pp[1];
            if(p1==null)continue;
            Field field = arg1.getClass().getDeclaredField(p1);
            if(field==null) continue;
            field.setAccessible(true);
            ret+=":"+field.get(arg1);
        }

        return ret;
    }

}

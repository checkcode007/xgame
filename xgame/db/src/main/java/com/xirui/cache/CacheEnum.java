package com.xirui.cache;


/**
 * 缓存类型
 *
 */
public class CacheEnum {
    public static final String KEY1= "KEY1";
    public enum Type{
        LOCAL(0,"local"),REMOTE(1,"remote"),BOTH(2,"both")
        ;
        private int k;
        private String name;


        Type(int k, String name) {
            this.k = k;
            this.name = name;
        }

        public int getK() {
            return k;
        }

        public String getName() {
            return name;
        }
        public static Type getType(int k){
            for (Type v : values()) {
                if(v.k == k ){
                    return v;
                }
            }
            return null;
        }


    }
}

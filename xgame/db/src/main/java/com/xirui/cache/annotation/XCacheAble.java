package com.xirui.cache.annotation;

@XCache
public @interface XCacheAble {
    String name() default "";
    String key() default "";
    boolean sync() default false;
}

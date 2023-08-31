package com.xirui.cache.annotation;

@XCache
public @interface XCachePut {
    String name() default "";
    String key() default "";
}

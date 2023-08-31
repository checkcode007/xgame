package com.xirui.cache.annotation;

@XCache
public @interface XCacheEvict {
    String name() default "";
    String key() default "";
}

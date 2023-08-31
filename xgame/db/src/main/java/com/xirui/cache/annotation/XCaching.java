package com.xirui.cache.annotation;

@XCache
public @interface XCaching {
    XCacheAble[] cacheAble() default {};
    XCachePut[] put() default {};
    XCacheEvict[] evict() default {};
}

package com.xirui.cache.annotation;

public @interface XCaching {
    XCacheAble[] cacheAble() default {};
    XCachePut[] put() default {};
    XCacheEvict[] evict() default {};
}

package com.xirui.cache.annotation;

import com.xirui.cache.CacheEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface XCacheEvict {
    CacheEnum.Type type() default CacheEnum.Type.LOCAL;
    String name() default "";
    String key() default "";
}

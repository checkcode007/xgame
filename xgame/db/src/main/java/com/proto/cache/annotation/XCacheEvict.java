package com.proto.cache.annotation;

import com.proto.cache.CacheEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface XCacheEvict {
    CacheEnum.Type type() default CacheEnum.Type.LOCAL;
    String group() default "";
    String name() default "";

    String[] params();
}

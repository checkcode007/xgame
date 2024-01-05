package com.proto.cache.annotation;

import com.proto.cache.CacheEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface XCacheAble {
    CacheEnum.Type type() default CacheEnum.Type.LOCAL;
    String group() default "";
    String name() default "";

    String[] params();
    boolean sync() default false;
}

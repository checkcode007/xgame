package com.xirui.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableNettyClient
//@EnableNettyServer
public @interface EnableNetty {
}

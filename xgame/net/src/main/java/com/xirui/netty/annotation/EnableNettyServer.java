package com.xirui.netty.annotation;

import com.xirui.netty.server.ServerBoot;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * netty服务器开关
 */
@Import(ServerBoot.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableNettyServer {
}

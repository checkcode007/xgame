package com.client.annotation;

import com.client.client.ClientBoot;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ClientBoot.class)
public @interface EnableNettyClient {
}

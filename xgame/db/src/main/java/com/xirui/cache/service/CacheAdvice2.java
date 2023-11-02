package com.xirui.cache.service;

import com.xirui.cache.CacheManager;
import com.xirui.cache.annotation.XCacheAble;
import com.xirui.cache.annotation.XCacheEvict;
import com.xirui.cache.bean.ICaches;
import com.xirui.cache.util.CacheUtil;
import com.xirui.util.FastJsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.StringJoiner;

/**
 * 缓存处理
 * @author zcj
 */
@Aspect
@Component
@Order(1)
public class CacheAdvice2 {
    private final Logger log = LoggerFactory.getLogger(CacheAdvice2.class);


    // 定义一个切点：所有被GetMapping注解修饰的方法会织入advice
    @Pointcut("@annotation(com.xirui.cache.annotation.XCacheAble)")
    private void able() {}
    @Pointcut("@annotation(com.xirui.cache.annotation.XCacheEvict)")
    private void evict() {}
    @Around("able()")
    public Object aroundAble(ProceedingJoinPoint joinPoint) throws Throwable{
        StringJoiner sj = new StringJoiner(",").add("aroundAble:"+joinPoint);
        log.info(sj+"======>start");
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = methodSignature.getMethod();
        sj.add(signature.getName()).add(signature.getDeclaringTypeName());
        XCacheAble annotation = method.getAnnotation(XCacheAble.class);
        String group = annotation.group();
        String name = annotation.name();
        String[] params = annotation.params();
        String redisK = CacheUtil.createKey(group,name,params,args);

        sj.add("redisK===>"+redisK);
        Object obj = joinPoint.proceed();
        sj.add("obj:"+obj);
        log.info(sj.toString());
        if(obj == null){
            return FastJsonUtil.parseJSONStr2JSONObject("{\"message\":\"illegal id\",\"code\":403}");
        }
        ICaches caches = CacheManager.ins.get(annotation.type());
        caches.put(name,redisK,(Serializable) obj);
        log.info(sj+"======>end");
        return obj;
    }

    @Around("evict()")
    public Object aroundEvict(ProceedingJoinPoint joinPoint) throws Throwable{
        StringJoiner sj = new StringJoiner(",").add("aroundEvict:"+joinPoint);
        log.info(sj+"======>start");
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = methodSignature.getMethod();
        sj.add(signature.getName()).add(signature.getDeclaringTypeName());
        XCacheEvict annotation = method.getAnnotation(XCacheEvict.class);
        String group = annotation.group();
        String name = annotation.name();
        String[] params = annotation.params();
        String redisK = CacheUtil.createKey(group,name,params,args);
        sj.add("redisK===>"+redisK);
        Object obj = joinPoint.proceed();
        sj.add("obj:"+obj);
        log.info(sj.toString());
        if(obj == null){
            return FastJsonUtil.parseJSONStr2JSONObject("{\"message\":\"illegal id\",\"code\":403}");
        }
        ICaches caches = CacheManager.ins.get(annotation.type());
        caches.evict(name,redisK);
        log.info(sj+"======>end");
        return obj;
    }

}

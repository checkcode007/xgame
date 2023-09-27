package com.xirui.cache.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.xirui.util.FastJsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import netscape.javascript.JSObject;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 缓存处理
 * @author zcj
 */
@Aspect
@Component
@Order(0)
public class CacheAdvice {
    private final Logger log = LoggerFactory.getLogger(CacheAdvice.class);


    // 定义一个切点：所有被GetMapping注解修饰的方法会织入advice
    @Pointcut("@annotation(com.xirui.cache.annotation.XCacheAble)")
    private void able() {}
    @Pointcut("@annotation(com.xirui.cache.annotation.XCacheEvict)")
    private void evict() {}

    /**
     * 定义一个切面，拦截 com.mutest.controller 包和子包下的所有方法
     * com.xirui.controller
     */
    @Pointcut("execution(* com.xirui.controller..*.*(..))")
    public void pointCut() {}
    // Before表示logAdvice将在目标方法执行前执行
    @Before("able()")
    public void beforeAble(){
        // 这里只是一个示例，你可以写任何处理逻辑
        System.err.println("请求的advice触发了----->before");
    }
    @After("able()")
    public void afterAble(){
        System.err.println("请求的advice触发了----->after");
    }

    @Around("able()")
    public Object aroundAble(ProceedingJoinPoint joinPoint) throws Throwable{
        System.err.println("请求的advice触发了----->around---->start");
        Object[] args = joinPoint.getArgs();
        System.err.println("请求的advice触发了----->around---->args:"+args);
        if(args!=null){
            for (Object arg : args) {
//                JSObject jsObject = (JSObject) arg;
                System.err.println("请求的advice触发了----->around-->:"+arg);
            }
        }
//        Long id = ((JSONObject) args[0]).getLong("id");
//        String name = ((JSONObject) args[0]).getString("name");
        Object obj = joinPoint.proceed();
        if(obj == null){
            return FastJsonUtil.parseJSONStr2JSONObject("{\"message\":\"illegal id\",\"code\":403}");
        }

//        // 修改入参
//        JSONObject object = new JSONObject();
//        object.put("id", 8);
//        object.put("name", "lisi");
//        objects[0] = object;
//
//        // 将修改后的参数传入
//        return joinPoint.proceed(objects);
        System.err.println("请求的advice触发了----->around----->end---obj:"+obj);
        return obj;
    }

    @Before("able()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("====doBefore方法进入了====");

        // 获取签名
        Signature signature = joinPoint.getSignature();
        // 获取切入的包名
        String declaringTypeName = signature.getDeclaringTypeName();
        // 获取即将执行的方法名
        String funcName = signature.getName();
        log.info("即将执行方法为: {}，属于{}包", funcName, declaringTypeName);

        // 也可以用来记录一些信息，比如获取请求的 URL 和 IP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求 URL
        String url = request.getRequestURL().toString();
        // 获取请求 IP
        String ip = request.getRemoteAddr();
        log.info("用户请求的url为：{}，ip地址为：{}", url, ip);
    }

    @After("able()")
    public void doAfter(JoinPoint joinPoint) {

        log.info("==== doAfter 方法进入了====");
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
        log.info("方法{}已经执行完", method);
    }

    @AfterReturning(pointcut = "able()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {

        Signature signature = joinPoint.getSignature();
        String classMethod = signature.getName();
        log.info("方法{}执行完毕，返回参数为：{}", classMethod, result);
        // 实际项目中可以根据业务做具体的返回值增强
        log.info("对返回参数进行业务上的增强：{}", result + "增强版");
    }
    @AfterThrowing(pointcut = "able()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
        // 处理异常的逻辑
        log.info("执行方法{}出错，异常为：{}", method, ex);
    }
}

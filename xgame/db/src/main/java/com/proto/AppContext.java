package com.proto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class AppContext implements ApplicationContextAware, ApplicationEventPublisherAware {

    private static ApplicationContext applicationContext;
    private static ApplicationEventPublisher applicationEventPublisher;

    private static List<String> devProfiles = Arrays.asList("dev","local","debug");
    private static List<String> localProfiles = Arrays.asList("local","debug");


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        AppContext.applicationEventPublisher = applicationEventPublisher;
    }
    public static String getActiveProfile(){
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        if (activeProfiles.length > 0){
            return activeProfiles[0];
        }
        return "default";
    }
    public static boolean isDev(){
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        for (int i = 0; i < activeProfiles.length; i++) {
            if (devProfiles.contains(activeProfiles[i])){
                return true;
            }
        }
        return false;
    }

    public static boolean isLocal(){
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        for (int i = 0; i < activeProfiles.length; i++) {
            if (localProfiles.contains(activeProfiles[i])){
                return true;
            }
        }
        return false;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public static  <T> T getBean(String name){
        return (T) applicationContext.getBean(name);
    }

    public static   <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static  <T> T getBean(String name, Class<T> clazz){
        return applicationContext.getBean(name, clazz);
    }


    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(Objects.nonNull(requestAttributes)){
            return requestAttributes.getRequest();
        }
        return null;
    }

    public static Object getRequestAttribute(String attributeName){
        if(Objects.nonNull(getRequest())){
            return getRequest().getAttribute(attributeName);
        }
        return null;
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static Object getSessionAttribute(String attributeName){
        return getSession().getAttribute(attributeName);
    }

    public static void setSessionAttribute(String attributeName, Object attribute){
        getSession().setAttribute(attributeName, attribute);
    }

    public static void removeSessionAttribute(String attributeName){
        getSession().removeAttribute(attributeName);
    }

}

package com.proto.controller;

import com.proto.mongo.TestMgo;
import com.proto.mongo.bean.TestBean;
import com.proto.mysql.CacheTestService;
import com.proto.mysql.StudentService;
import com.proto.mysql.entity.Student;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Api(tags="测试")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    TestMgo testMgo;
    @Autowired
    StudentService studentService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    CacheTestService cacheTestS;
    @RequestMapping("/cache")
    public String cache() {
        CacheTestService.PPTest ppTest = new CacheTestService.PPTest();
        ppTest.setPt1(100);
        ppTest.setPt2("dddd");
        cacheTestS.add(ppTest,"t2",1,2L,3f,"pp");
        return "Hello Spring Boot!";
    }
    @RequestMapping("/cacheDel")
    public String cacheDel() {
        CacheTestService.PPTest ppTest = new CacheTestService.PPTest();
        ppTest.setPt1(100);
        ppTest.setPt2("dddd");
        cacheTestS.del(ppTest,"t2",1,2L,3f,"pp");
        return "Hello Spring Boot---->del";
    }
    @RequestMapping("/cache1")
    public String cache1() {
        return "Hello Spring Boot!";
    }
    @RequestMapping("/hello")
        public String hello() {
            TestBean testBean = new TestBean("1","zhang1");

            testMgo.save(1l,testBean);
            return "Hello Spring Boot!";
    }
    @RequestMapping("/all")
    public String all() {
        List<Student> list =  studentService.getAll();
        String s = "null";
        if(list != null){
            s = Arrays.toString(list.toArray());
        }

        return s;
    }
    @RequestMapping("/redis")
    public String redis() {
       String v = redisTemplate.opsForValue().get("TEST:T2");

        return v;
    }

}

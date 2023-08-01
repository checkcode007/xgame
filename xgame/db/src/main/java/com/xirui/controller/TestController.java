package com.xirui.controller;

import com.xirui.mongo.TestMgo;
import com.xirui.mongo.bean.TestBean;
import com.xirui.mysql.StudentService;
import com.xirui.mysql.entity.Student;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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
}
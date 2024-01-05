package com.proto.controller;


import com.proto.service.Service1;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="测试")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    Service1 service1;
    @RequestMapping("/cache")
    public String test() {
        try {
            for (int i = 0; i <3 ; i++) {
                service1.task1();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Hello Spring Boot!";
    }

}

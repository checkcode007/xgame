package com.proto;

import com.proto.service.Service1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class XTest {

    @Autowired
    Service1 service1;
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(XTest.class, args);
        System.err.println();
        Thread.sleep(10000);
    }

    public  void test(){
        try {
            service1.task1();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

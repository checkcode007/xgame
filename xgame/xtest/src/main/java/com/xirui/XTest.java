package com.xirui;

import com.xirui.service.Service1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Serial;

@SpringBootApplication
public class XTest {

    @Autowired
    Service1 service1;
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(XTest.class, args);
        System.err.println();

        Thread.sleep(10000);
    }
}

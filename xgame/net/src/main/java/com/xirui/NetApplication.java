package com.xirui;

import com.xirui.annotation.EnableNetty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableNetty
public class NetApplication {
    public static void main(String[] args) {
        SpringApplication.run(NetApplication.class, args);
    }
}

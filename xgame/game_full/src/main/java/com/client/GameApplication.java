package com.client;

import com.client.netty.annotation.EnableNettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNettyServer
public class GameApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(GameApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.client;

import com.client.annotation.EnableNettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNettyClient
public class ClientApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ClientApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.xirui;

import com.xirui.annotation.EnableNettyClient;
import com.xirui.bean.MessageBean;
import com.xirui.client.ClientBoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNettyClient
public class ClientApplication {

//    @Autowired
//    ClientBoot clientBoot;
    public static void main(String[] args) {
        try {
            SpringApplication.run(ClientApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private  void test(){
//        try {
//            clientBoot.sendMsg(new MessageBean("eqwe"));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}

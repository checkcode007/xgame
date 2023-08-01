package com.xirui;

import com.xirui.mongo.TestMgo;
import com.xirui.mongo.bean.TestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class, args);

//        TestMgo testMgo = new TestMgo();
//        TestBean testBean = new TestBean();
//        testMgo.save(1l,testBean);
    }
}

package com.xirui.controller;

import org.springframework.stereotype3.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//@Controller
//@RequestMapping("/hello")
public class HelloController {
//    @RequestMapping("/string")
    public String returnString() {
        return "string";
    }
}

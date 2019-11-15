package cn.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLOutput;

@Controller
public class UserController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {

        System.out.println("UserController.hello()");
        return "ok";
    }

}

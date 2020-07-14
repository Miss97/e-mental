package com.emental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SingInController {

    @RequestMapping("/")
    public String index(){
        return "signIn";
    }

    @RequestMapping("/signIn")
    public String signIn(){
        return "welcome";
    }

    @RequestMapping("/signUp")
    public void signUp(){
    }

    @RequestMapping("/getCalender")
    public String getCalender(){
        return "calender";
    }
}

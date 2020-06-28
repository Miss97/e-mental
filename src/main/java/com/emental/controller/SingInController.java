package com.emental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SingInController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/signIn")
    public String signIn(){
        return "signIn";
    }

    @RequestMapping("/signUp")
    public void signUp(){
    }
}

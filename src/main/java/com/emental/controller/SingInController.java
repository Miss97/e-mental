package com.emental.controller;

import com.emental.dao.mapper.EmUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SingInController {
    @Autowired
    private EmUserInfoMapper emUserInfoMapper;

    @RequestMapping("/")
    public String index(){
        return "signIn";
    }

    @RequestMapping("/signIn")
    public String signIn(String username,String password){
        int flag = emUserInfoMapper.loginVerifi(username,password);
        if (flag==0)
            return "signIn" ;
        return "redirect:/welcome";
    }

    @RequestMapping("/signUp")
    public void signUp(){
    }

}

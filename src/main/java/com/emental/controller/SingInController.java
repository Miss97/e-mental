package com.emental.controller;

import com.emental.dao.mapper.EmUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class SingInController {
    @Autowired
    private EmUserInfoMapper emUserInfoMapper;

    @RequestMapping("/")
    public String index(){
        return "signIn";
    }

    @RequestMapping("/signIn")
    public String signIn(HttpServletRequest request, HttpServletResponse response, String username, String password){
        int flag = emUserInfoMapper.loginVerifi(username,password);
        if (flag==0){
            return "signIn" ;
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("username",username);
        }
        return "redirect:/welcome";
    }

    @RequestMapping("/signUp")
    public void signUp(){

    }

    @RequestMapping("/signOut")
    public String signOut(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        return "redirect:/";
    }

}

package com.emental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/getDashboard")
    public String getDashboard(){
        return "blankpage";
    }

    @RequestMapping("/getMoodDiary")
    public String getMoodDiary(){
        return "blankpage";
    }

    @RequestMapping("/getCalender")
    public String getCalender(){
        return "calender";
    }

    @RequestMapping("/getReflection")
    public String getReflection(){
        return "blankpage";
    }

    @RequestMapping("/getLifestyleHelper")
    public String getLifestyleHelper(){
        return "blankpage";
    }

    @RequestMapping("/getRecommendation")
    public String getRecommendation(){
        return "blankpage";
    }
}

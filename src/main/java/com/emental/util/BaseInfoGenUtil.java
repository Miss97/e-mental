package com.emental.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseInfoGenUtil {
    private final static String idCode = "FXWERTYUI456789GHJCVBNM123QKLZ0OPASD";
    private final static Date date = new Date();
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public static String getDataId(int count){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<count;i++){
            sb.append(idCode.charAt((int)(Math.random()*idCode.length())));
        }
        return sb.toString();
    }

    public static String getNowDate(){
        return sdf.format(date);
    }

    public static String getUsername(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return (String) request.getSession().getAttribute("username");
    }
}

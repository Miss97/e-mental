package com.emental.config;

import com.emental.interceptor.SignInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInterceptor())
                .excludePathPatterns("/signIn","/signUp","/signInVerify","/signUpVerify","/usernameVerify","/emailVerify","/accountActivation","/resetPass","/resetPassAlertPage","/resetPassword","/doResetPassword","/","/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
                                    "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg")
                .addPathPatterns("/**");
    }

}

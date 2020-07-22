package com.emental;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.emental.dao.mapper")
public class EmentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmentalApplication.class, args);
    }

}

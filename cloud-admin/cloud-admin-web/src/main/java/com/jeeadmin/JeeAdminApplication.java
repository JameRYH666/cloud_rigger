package com.jeeadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jeeadmin.mapper")
public class JeeAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeeAdminApplication.class, args);
    }
}

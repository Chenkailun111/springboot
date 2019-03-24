package com.itqf.dtboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//扫描servlet等相关类所在的包
@ServletComponentScan(basePackages = "com.itqf.dtboot.config")
@MapperScan("com.itqf.dtboot.dao")
public class SpringbootRzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRzApplication.class, args);
    }
}

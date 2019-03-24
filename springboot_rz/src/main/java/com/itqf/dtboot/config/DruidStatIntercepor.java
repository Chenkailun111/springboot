package com.itqf.dtboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = "classpath:applicationContext-bean.xml")
public class DruidStatIntercepor {
}

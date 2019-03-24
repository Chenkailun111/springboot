package com.itqf.dtboot.log;

import java.lang.annotation.*;

/**
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)//注解能够被反射读取到
@Documented
public @interface MyLog {

    //成员变量
    String value();

}

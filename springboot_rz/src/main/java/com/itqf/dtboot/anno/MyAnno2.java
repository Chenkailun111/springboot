package com.itqf.dtboot.anno;

import java.lang.annotation.*;

/*
自定义注解
 默认注解在编译时有效
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)//so they may be read reflectively.
@Documented  //doc文档
@Inherited// 可以被继承
public @interface  MyAnno2 {

    String  name() default "自定义注解";

    String value();

}

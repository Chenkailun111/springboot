package com.itqf.dtboot.anno;

import org.aspectj.weaver.ast.Test;

import java.lang.annotation.Annotation;

//@MyAnno2(name="testAnno")
//自定义注解的value成员变量，在使用注解时，可以省略
@MyAnno2("ceshi")
public class TestAnno {

    public static void main(String[] args) throws  Exception {

       //怎么得到一个类的Class对象
        Class clazz = TestAnno.class;
        Class clazz2 = Class.forName("com.itqf.dtboot.anno.TestAnno");
        Class clazz3 =  new TestAnno().getClass();

        Annotation annotation [] =  clazz.getAnnotations();
        System.out.println(annotation.length);
        for (Annotation annotation1 : annotation) {
            System.out.println(annotation1.annotationType());
            if (annotation1 instanceof  MyAnno2){
                MyAnno2 anno2 = (MyAnno2) annotation1;
                System.out.println(anno2.name()+"---"+anno2.value());
            }
        }




    }


}

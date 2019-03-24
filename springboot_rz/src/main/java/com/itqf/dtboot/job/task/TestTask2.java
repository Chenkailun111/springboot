package com.itqf.dtboot.job.task;

import org.springframework.stereotype.Component;

@Component("testTask2")
public class TestTask2 {

    public void test(){
        System.out.println("第二个定时任务!");
    }
}

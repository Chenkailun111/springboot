package com.itqf.dtboot.job.task;

import org.springframework.stereotype.Component;

@Component("testTask")
public class TestTask {

    public  void test(){
        System.out.println("第一个定时任务！！");
        //具有的 某个需要定时执行的业务逻辑
    }


}

package com.itqf.dtboot.job;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.itqf.dtboot.entity.ScheduleJob;
import com.itqf.dtboot.job.task.TestTask;
import com.itqf.dtboot.job.task.TestTask2;
import com.itqf.dtboot.utils.SpringContextUtils;
import com.itqf.dtboot.utils.StringUtils;
import com.itqf.dtboot.utils.SysConstant;
import org.apache.catalina.core.ApplicationContext;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * 任务类
 * 实现job接口 或者继承QuartzJobBean  都可以
 */

public class MyQuartzJobBean extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
       // System.out.println("helloword！！！！！！");
        //需要定时执行的代码
        //备份数据库
        //定时结算工资
       // 调用其他的业务类
        //怎么做到不同的定时任务执行不同的业务逻辑？？？
        //beanName:testTask 任务
      /*  TestTask testTask = new TestTask();
        testTask.test();

        TestTask2 testTask2 = new TestTask2();
        testTask2.test();*/

        try {
            JobDataMap jobDataMap =  context.getJobDetail().getJobDataMap();
            //System.out.println(jobDataMap.get(SysConstant.SCHEDULE_DATA_KEY));
            String json = (String)jobDataMap.get(SysConstant.SCHEDULE_DATA_KEY);
            ScheduleJob scheduleJob = JSON.parseObject(json,ScheduleJob.class);

            //获得要调用的task的beanName  和 methodName
            String beanName = scheduleJob.getBeanName();//testTask  testTask1 testTask2
            String methodName = scheduleJob.getMethodName();//test

            //如何根据beanName从spring容器中得到一个具体的对象
            Object object = SpringContextUtils.getBean(beanName);

            //通过反射调用方法
            // method.invoke(object);
            Class clazz = object.getClass();
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            //得到参数
            String params = scheduleJob.getParams();
            if (StringUtils.isNotEmpty(params)){
                method.invoke(object,params);
            }
           else{
                method.invoke(object);
            }

            // 记录定时任务执行的日志  schedule_job_log


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

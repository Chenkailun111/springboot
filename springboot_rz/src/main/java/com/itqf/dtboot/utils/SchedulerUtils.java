package com.itqf.dtboot.utils;

import com.alibaba.fastjson.JSON;
import com.itqf.dtboot.entity.ScheduleJob;
import com.itqf.dtboot.job.MyQuartzJobBean;
import org.quartz.*;

/**
 * 工具类
 * 完成定时任务的
 * 创建，修改，删除，暂停，恢复等功能
 *
 */
public class SchedulerUtils {

    /**
     * 创建定时任务
     * Scheduler   JobDetail  Trigger
     * @param scheduleJob  实体类，一个scheduleJob对象描述一个定时任务
     * @param scheduler  任务调度器
     */
    public static void  createScheduler(ScheduleJob scheduleJob, Scheduler scheduler){
        System.out.println("创建定时任务--->id:"+scheduleJob.getJobId()+"--->"+scheduleJob.getBeanName());
        //1,jobDetail
        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJobBean.class).
                withIdentity(SysConstant.JOB_KEY_PREFIX+scheduleJob.getJobId()).build();
        //向 MyQuartzJobBean  传递数据
        String json = JSON.toJSONString(scheduleJob);
        jobDetail.getJobDataMap().put(SysConstant.SCHEDULE_DATA_KEY,json);

        //2,创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()))
                .withIdentity(SysConstant.TRIGGER_KET_PREFIX+scheduleJob.getJobId()).build();


        //3,schedule已经创建好，在QuartzConfig类中已经创建
        //4,注册任务和触发器
        try {
            scheduler.scheduleJob(jobDetail,trigger);
            //5,启动任务
           // scheduler.start();

        } catch (SchedulerException e) {
            throw  new RRException("创建任务失败！");
        }
    }

    public static  void runJob(ScheduleJob scheduleJob ,Scheduler scheduler){
        //运行任务

        try {
            scheduler.triggerJob(JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+scheduleJob.getJobId()));
        } catch (SchedulerException e) {
            throw  new RRException("运行任务失败！");
        }
    }

    /**
     * 暂停执行
     */
    public static void pauseJob(ScheduleJob scheduleJob,Scheduler scheduler){
        try {
            scheduler.pauseJob(JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+scheduleJob.getJobId()));
        } catch (SchedulerException e) {
            throw  new RRException("暂停任务失败！");
        }
    }

    public static void resumeJob(ScheduleJob scheduleJob ,Scheduler scheduler){

        try {
            scheduler.resumeJob(JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+scheduleJob.getJobId()));
        } catch (SchedulerException e) {
            throw  new RRException("恢复任务失败！");
        }
    }

    /**
     * 修改任务 -->修改触发的条件
     * @param scheduleJob
     * @param scheduler
     */
    public  static void updateJob(ScheduleJob scheduleJob,Scheduler scheduler){
        try {
            //1,先得到要修改的任务的触发器的TriggerKey
            TriggerKey key = TriggerKey.triggerKey(SysConstant.TRIGGER_KET_PREFIX+scheduleJob.getJobId());
            //2,得到原来的触发器对象
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(key);
            //3,更新触发器的表达式
            trigger=trigger.getTriggerBuilder()
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())).build();
            //4,重置触发器
            scheduler.rescheduleJob(key,trigger);
        } catch (SchedulerException e) {
            throw  new RRException("修改任务失败",e);
        }

    }

    public  static void  delJob(ScheduleJob scheduleJob,Scheduler scheduler){
        try {
            scheduler.deleteJob(JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+scheduleJob.getJobId()));
        } catch (SchedulerException e) {
            throw  new RRException("删除任务失败！");
        }
    }






}

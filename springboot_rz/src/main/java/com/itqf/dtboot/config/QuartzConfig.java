package com.itqf.dtboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * quartz配置类
 * JobDetail
 * Trigger  触发器  出发条件
 * Scheduler
 */
@Configuration
public class QuartzConfig {


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("dataSource") DataSource dataSource){

        //任务的持久化保存到数据库中
        //quartz参数
        Properties prop = new Properties();
        //配置实例
        prop.put("org.quartz.scheduler.instanceName", "MyScheduler");//实例名称
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        //线程池配置
        prop.put("org.quartz.threadPool.threadCount", "5");
        //JobStore配置
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");//表前缀


        SchedulerFactoryBean schedulerFactoryBean =  new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setQuartzProperties(prop);
        schedulerFactoryBean.setStartupDelay(5);//设置延迟启动时间
        schedulerFactoryBean.setAutoStartup(true);//设置任务自动执行

        return schedulerFactoryBean;
    }

}

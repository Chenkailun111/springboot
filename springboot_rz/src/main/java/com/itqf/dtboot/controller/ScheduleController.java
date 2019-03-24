package com.itqf.dtboot.controller;

import com.itqf.dtboot.entity.ScheduleJob;
import com.itqf.dtboot.service.ScheduleJobService;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.TableResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ScheduleController {
    @Resource
    private ScheduleJobService scheduleJobService;


    @RequestMapping("/schedule/job/save")
    public R saveSchedule(@RequestBody ScheduleJob scheduleJob){

      return  scheduleJobService.saveScheduleJob(scheduleJob);
    }

    /**
     * 分页查询任务
     */
    @RequestMapping("/schedule/job/list")
    public TableResult jobList(int offset,int limit,String search){

        return scheduleJobService.jobList(offset,limit,search);

    }

    /**
     * 删除任务
     */
    @RequestMapping("/schedule/job/del")
    public R delJob(@RequestBody List<Long> ids){
       return  scheduleJobService.delJob(ids);
    }

    /**
     * 恢复
     */
    @RequestMapping("/schedule/job/resume")
    public R resumeJob(@RequestBody List<Long> ids){

        return scheduleJobService.resumeJob(ids);
    }
    /**
     * 暂停
     */
    @RequestMapping("/schedule/job/pause")
    public R pauseJob(@RequestBody List<Long> ids){
       return scheduleJobService.pauseJob(ids);
    }
    /**
     * 执行
     */
    @RequestMapping("/schedule/job/run")
    public R runJob(@RequestBody List<Long> ids){
        return  scheduleJobService.runJob(ids);//执行一次
    }

    /**
     * 根据id查询任务
     */
    @RequestMapping("/schedule/job/info/{jobId}")
    public R scheduleInfo(@PathVariable long jobId){
      return  scheduleJobService.scheduleInfo(jobId);
    }

    @RequestMapping("/schedule/job/update")
    public R updateSchedule(@RequestBody ScheduleJob scheduleJob){
      return  scheduleJobService.update(scheduleJob);
    }


}

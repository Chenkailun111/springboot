package com.itqf.dtboot.service;

import com.itqf.dtboot.entity.ScheduleJob;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.TableResult;

import java.util.List;

public interface ScheduleJobService {


    public R saveScheduleJob(ScheduleJob scheduleJob);

    public TableResult jobList(int offset,int limit,String search);

    public R delJob(List<Long> ids) ;
    public R resumeJob(List<Long> ids) ;
    public R pauseJob(List<Long> ids) ;
    public R runJob(List<Long> ids) ;

    public  R scheduleInfo(long jobId);

    public R update(ScheduleJob scheduleJob);

}

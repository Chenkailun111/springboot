package com.itqf.dtboot.service.impl;

import com.itqf.dtboot.dao.SysLogMapper;
import com.itqf.dtboot.entity.SysLog;
import com.itqf.dtboot.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public void saveSysLog(SysLog log) {
        sysLogMapper.insertSelective(log);
    }
}

package com.itqf.dtboot.service.impl;

import com.itqf.dtboot.dao.SysRoleMapper;
import com.itqf.dtboot.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional

public class RoleServiceImpl  implements RoleService {

    //dao
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<String> findRoleByUserId(long userId) {

        return sysRoleMapper.findRoleNameByUserId(userId);
    }
}

package com.itqf.dtboot.service;

import com.itqf.dtboot.entity.SysUser;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.TableResult;

import java.util.List;

public interface UserService {

    public TableResult findUser(int offset,int limit,String search);

    public R save(SysUser sysUser);

    public R findById(long userId);

    public R update(SysUser sysUser);

    public R delete(List<Long> ids);

    public SysUser findByUsername(String username);
}

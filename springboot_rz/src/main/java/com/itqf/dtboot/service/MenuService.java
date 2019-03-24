package com.itqf.dtboot.service;

import com.itqf.dtboot.entity.SysMenu;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.TableResult;

import java.util.List;

public interface MenuService {

    public TableResult findMenu(int offset, int limit,String search);

    public R del(List<Long> ids);

    public  R findMenuNotButton();

    public R  insert(SysMenu sysMenu);

    public R findById(long menuId);

    public R updateMenu(SysMenu menu);

    public List<String> findPermsByUserId(long userId);

    public R findMenu(long userId);

}

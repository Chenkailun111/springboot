package com.itqf.dtboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.dtboot.dao.SysMenuMapper;
import com.itqf.dtboot.entity.SysMenu;
import com.itqf.dtboot.entity.SysMenuExample;
import com.itqf.dtboot.service.MenuService;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.TableResult;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    //注入mapper
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     *
     * @param offset  起始记录
     * @param limit  每页显示多少条
     * @return
     */
    @Override
    public TableResult findMenu(int offset, int limit,String search) {

        //分页   * @param pageNum  页码
        //     * @param pageSize 每页显示数量
       // PageHelper.startPage()
        /**
         * offset:起始记录
         * limit 每页显示多少条
         */
        PageHelper.offsetPage(offset,limit);

      // select * from sys_menu where name like ?

       // List<SysMenu> list = sysMenuMapper.selectByExample(null);//全部
        //封装查询条件（where后的条件）
        SysMenuExample example = null;
        if (search!=null&&!"".equals(search)){
           example = new SysMenuExample();
            SysMenuExample.Criteria  criteria=  example.createCriteria();
            criteria.andNameLike("%"+search+"%");
        }
        List<SysMenu> list = sysMenuMapper.selectByExample(example);

        PageInfo<SysMenu> pageInfo = new PageInfo<>(list);

        TableResult result = new TableResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    @Override
    public R del(List<Long> ids) {

        //系统菜单不能删除
        for (Long id : ids) {
            if (id<29){
                return  R.error("系统菜单不能删除！");
            }
        }

        //sysMenuMapper.deleteByPrimaryKey()//
        //where menu_id in (1,2);
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria  c = example.createCriteria();

        c.andMenuIdIn(ids);

        int i = sysMenuMapper.deleteByExample(example);

        return i>0?R.ok("删除成功"):R.error("删除失败");
    }

    @Override
    public R findMenuNotButton() {
        List<SysMenu> list = sysMenuMapper.findMenuNotButton();
        //在后台添加一个一级目录
        //为了可以添加和系统菜单同级的目录
        SysMenu menu = new SysMenu();
        menu.setMenuId(0l);
        menu.setParentId(-1l);
        menu.setName("一级菜单");
        menu.setType(0);
        list.add(menu);
        return R.ok("").put("menuList",list);
    }

    @Override
    public R insert(SysMenu sysMenu) {

        int i  = sysMenuMapper.insertSelective(sysMenu);
        return i>0?R.ok("新增成功"):R.error("新增失败");
    }

    @Override
    public R findById(long menuId) {
        SysMenu menu = sysMenuMapper.selectByPrimaryKey(menuId);
        if (menu!=null){
            return R.ok().put("menu",menu);
        }
        return R.error("查无菜单");
    }

    @Override
    public R updateMenu(SysMenu menu) {
        int i = sysMenuMapper.updateByPrimaryKeySelective(menu);
        return i>0?R.ok("修改成功"):R.error("修改失败");
    }

    @Override
    public List<String> findPermsByUserId(long userId) {
        List<String> list  = sysMenuMapper.findPermsByUserId(userId);
        Set<String> set = new HashSet<String>();
        for (String s : list) {
            if (s!=null&&!s.equals("")){
                String ss[] = s.split(",");
                for (String s1 : ss) {
                    set.add(s1);
                }
            }

        }
        //set->list
        List<String> perms = new ArrayList<>();
        perms.addAll(set);

        return perms;
    }

    /**
     /**
     * 获取用户能够访问的菜单列表
     * {
     *   "menuList": [{
     *     "menuId": 1,
     *     "parentId": 0,
     *     "parentName": null,
     *     "name": "系统管理",
     *     "url": null,
     *     "perms": null,
     *     "type": 0,
     *     "icon": "fa fa-cog",
     *     "orderNum": 0,
     *     "open": null,
     *     "list": [{
     *       "menuId": 2,
     *       "parentId": 1,
     *       "parentName": null,
     *       "name": "用户管理",
     *       "url": "sys/user.html",
     *       "perms": null,
     *       "type": 1,
     *       "icon": "fa fa-user",
     *       "orderNum": 1,
     *       "open": null,
     *       "list": null
     *     }],
     *   "code": 0,
     *   "permissions": ["sys:schedule:info", "sys:menu:update", "sys:menu:delete", "sys:config:info", "sys:generator:list", "sys:menu:list", "sys:config:save", "sys:menu:perms", "sys:config:update", "sys:schedule:resume", "sys:user:delete", "sys:config:list", "sys:user:update", "sys:role:list", "sys:menu:info", "sys:menu:select", "sys:schedule:update", "sys:schedule:save", "sys:role:select", "sys:user:list", "sys:menu:save", "sys:role:save", "sys:schedule:log", "sys:role:info", "sys:schedule:delete", "sys:role:update", "sys:schedule:list", "sys:user:info", "sys:generator:code", "sys:schedule:run", "sys:config:delete", "sys:role:delete", "sys:user:save", "sys:schedule:pause", "sys:log:list"]
     * }
     * @param userId
     * @return
     */
    @Override
    public R findMenu(long userId) {
        //系统菜单
        //任务管理
        List<Map<String,Object>> parentMenu = sysMenuMapper.findParentMenuByUserId(userId);
       /* Map<String,Object> map = new HashMap<>();
        map.put("menuList",parentMenu);*/
        for (Map<String, Object> map : parentMenu) {
            Long menuId = (Long) map.get("menu_id");
            //查询菜单下的子菜单
            List<Map<String,Object>> menuMap = sysMenuMapper.findMenuByUserId(userId,menuId);
            map.put("list",menuMap);
        }

        List<String> perms = this.findPermsByUserId(userId);//sysMenuMapper.findPermsByUserId(userId);

        return R.ok().put("menuList",parentMenu).put("permissions",perms);
    }
}
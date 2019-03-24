package com.itqf.dtboot.controller;

import com.itqf.dtboot.entity.SysMenu;
import com.itqf.dtboot.entity.SysUser;
import com.itqf.dtboot.log.MyLog;
import com.itqf.dtboot.service.MenuService;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.ShiroUtils;
import com.itqf.dtboot.utils.TableResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MenuController {

    //依赖service
    @Resource
    private MenuService menuService;

    @MyLog("查看菜单列表")
    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/sys/menu/list")
    public TableResult findMenyuList(int offset ,int limit,String search){//{total:800,rows:[{},{}]}

       return  menuService.findMenu(offset,limit,search);
    }
    @MyLog("删除菜单")
    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("/sys/menu/del")
    public R del(@RequestBody List<Long> ids){// [1,2];

        return  menuService.del(ids);
    }

    /*
     获取树形结构所需要的数据
     */
    @MyLog("查看菜单")
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/select")
    public R selectTreeMenu(){
        return menuService.findMenuNotButton();
    }

    /**
     * 新增
     * @param menu
     * @return
     */
    @MyLog("新增菜单")
    @RequiresPermissions("sys:menu:save")
    @RequestMapping("/sys/menu/save")
    public R insert(@RequestBody SysMenu menu){

        return menuService.insert(menu);
    }

    /**
     * 修改开始
     */
    // sys/menu/info?menuId=1
    @MyLog("根据编号查看菜单")
    @RequiresPermissions("sys:menu:info")
    @RequestMapping("/sys/menu/info/{menuId}")
    public R findById(@PathVariable long menuId){//@PathVariable 得到url中占位符{}的值
        return menuService.findById(menuId);
    }

    @MyLog("修改菜单")
    @RequiresRoles("admin")
    @RequestMapping("/sys/menu/update")
    public  R update(@RequestBody SysMenu menu){
        return menuService.updateMenu(menu);
    }
    //修改结束

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
     * @return
     */
    @RequestMapping("sys/menu/user")
    public R getMenuList(){

        //得到当前登录的用户
        SysUser sysUser = ShiroUtils.getCurrentUser();

        return menuService.findMenu(sysUser.getUserId());

    }

}

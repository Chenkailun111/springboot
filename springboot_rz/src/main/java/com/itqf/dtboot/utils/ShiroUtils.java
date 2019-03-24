package com.itqf.dtboot.utils;

import com.itqf.dtboot.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * shiro的工具类
 */
public class ShiroUtils {

    public static  SysUser getCurrentUser(){
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    public static Session getSession(){
        return  SecurityUtils.getSubject().getSession();
    }

    public static void setAttribute(String key,String value){
        getSession().setAttribute(key,value);
    }

    public static  Object getAttribute(String key){
       return    getSession().getAttribute(key);
    }

    public static void logout(){
        SecurityUtils.getSubject().logout();
    }




}

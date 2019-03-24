package com.itqf.dtboot.aspect;

import com.alibaba.fastjson.JSON;
import com.itqf.dtboot.entity.SysLog;
import com.itqf.dtboot.log.MyLog;
import com.itqf.dtboot.service.SysLogService;
import com.itqf.dtboot.utils.HttpContextUtils;
import com.itqf.dtboot.utils.IPUtils;
import com.itqf.dtboot.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class MyLogAspect {
    @Autowired
    private SysLogService sysLogService;

    //匹配的是方法
    //@Pointcut("execution(* com.itqf.dtboot.service.impl.*.*(..))")
    //@annotation  描述的是注解
    @Pointcut("@annotation(com.itqf.dtboot.log.MyLog)")
    private  void myPointCut(){}

    /**
     * 后置通知（增强）
     * JoinPoint 得到目标方法  参数等
     */
    @AfterReturning(pointcut = "myPointCut()")
    public void after(JoinPoint joinPoint){
        System.err.println("----->后置通知");
        //获得目标方法 参数      操作  操作人
        //System.out.println( "args"+joinPoint.getArgs()); 参数
       // System.out.println( "getSignature"+joinPoint.getSignature().getName());方法
        //System.out.println("target"+joinPoint.getTarget()); 类
        try {
            SysLog sysLog = new SysLog();
            sysLog.setCreateDate(new Date());
            sysLog.setIp(IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));//操作人的ip地址

            String methodName = joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName();
            sysLog.setMethod(methodName);

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method =  methodSignature.getMethod();
            //得到修饰该方法的指定的注解
            MyLog myLog =  method.getAnnotation(MyLog.class);
            String operation = "";
            if (myLog!=null){
                operation = myLog.value();
            }
            sysLog.setOperation(operation);
            String username  =ShiroUtils.getCurrentUser().getUsername();
            sysLog.setUsername(username);
            Object []obj =  joinPoint.getArgs();

            //把数组转成json字符串
            //fastjson
            //gson
            //jackson
            sysLog.setParams(JSON.toJSONString(obj));//调用方法传递的参数  实参


            // 保存到数据库
            sysLogService.saveSysLog(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
package com.itqf.dtboot.config;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *  public static final String PARAM_NAME_USERNAME = "loginUsername";
 *     public static final String PARAM_NAME_PASSWORD = "loginPassword";
 *     public static final String PARAM_NAME_ALLOW = "allow";
 *     public static final String PARAM_NAME_DENY = "deny";
 *     配置使用sql监控功能的账号和密码
 *     及白名单和黑名单:黑名单和白名单都有，则取deny
 *     如果配置了白名单，则除了白名单以外的都不能访问
 *     resetEnable:禁用页面重置功能
 */
@WebServlet(name = "druidStatViewServlet",urlPatterns = "/druid/*",
initParams =
        {@WebInitParam(name = "loginUsername",value = "jack"),
        @WebInitParam(name="loginPassword",value="jack"),
        @WebInitParam(name = "allow",value="127.0.0.1,192.168.82.99"),
        @WebInitParam(name="deny",value="192.168.82.100"),
        @WebInitParam(name="resetEnable",value = "false")
})
public class DruidStatViewServlet extends StatViewServlet {
}

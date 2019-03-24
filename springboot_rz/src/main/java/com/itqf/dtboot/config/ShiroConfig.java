package com.itqf.dtboot.config;

import com.itqf.dtboot.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro config
 * applicationContext-shiro.xml
 */
@Configuration
//@ImportResource("")
public class ShiroConfig {

    /**
     * 创建sessionManager 管理会话
     * @return
     */
        @Bean("sessionManager")
        public SessionManager sessionManager(){
            //创建sessoinManager
            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            //设置session的过期时间
            sessionManager.setGlobalSessionTimeout(1000*60*30);
            //设置清理会话的线程
            sessionManager.setSessionValidationSchedulerEnabled(true);
            //不允许地址栏拼接sessionid
            sessionManager.setSessionIdUrlRewritingEnabled(false);
            return sessionManager;
        }

    /**
     *SecurityManager
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager(UserRealm userRealm,SessionManager sessionManager){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);

        //记住我
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        Cookie cookie = cookieRememberMeManager.getCookie();
        cookie.setMaxAge(6000);
        cookie.setPath("/");

        //设置记住我功能
        securityManager.setRememberMeManager(cookieRememberMeManager);

        //缓存管理
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        //设置缓存
        securityManager.setCacheManager(ehCacheManager);
        return  securityManager;
    }

    /**
     * 配置shiroFilter
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截路径的详细设置
        //登录页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //默认登录成功的页面
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //没有权限时候跳转的页面
        //shiroFilterFactoryBean.setUnauthorizedUrl("unauthorized.json");
        shiroFilterFactoryBean.setUnauthorizedUrl("unauthorized.html");
        //什么Map能保证存取有序？？
        Map<String,String > map = new LinkedHashMap<>();
        map.put("/public/**","anon");
        map.put("/sys/login","anon");
        map.put("/captcha.jpg","anon");//验证码
        map.put("/logout","logout");
        map.put("/**","user");//记住我
        map.put("/**","authc");//所有请求必须认证后才能
        //map.put("/sys/menu/list","perms[sys:menu:list]")
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return  shiroFilterFactoryBean;
    }

    /**
     * 配置lifecycleBean
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor (){
        return  new LifecycleBeanPostProcessor();
    }

    /**
     * 设置shiro的注解在spring容器中使用
     */
    @Bean("defaultAdvisorAutoProxyCreator")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();

        //设置使用到shiro注解的类底层创建代理对象的方式 cglib 因为在controller使用注解
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }



}

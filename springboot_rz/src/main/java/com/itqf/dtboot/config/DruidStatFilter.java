package com.itqf.dtboot.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 等价于在web.xml中配置
 * <filter>
 *     <filter-name></filter-name>
 *     <filter-class></filter-class>
 *     <init-param>
 *         <param-name>exclusions</param-name>
 *         <param-value></param-value>
 *     </init-param>
 * </filter>
 * <filter-mapping>
 *
 * </filter-mapping>
 *
 * 配置过滤规则，忽略不拦截的资源
 */
@WebFilter(filterName = "druidStatFilter",urlPatterns = "/*",
        initParams ={@WebInitParam(name="exclusions",value = "*.js,*.css,*.jpg,*.png,*.gif,*.bmp,/druid/*")} )
public class DruidStatFilter extends WebStatFilter {
}

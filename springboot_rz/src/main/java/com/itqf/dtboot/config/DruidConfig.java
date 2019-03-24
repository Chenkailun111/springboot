package com.itqf.dtboot.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DruidConfig {

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")//spring.datasource 开头的属性
    public DataSource dataSource(){
        System.out.println("------>初始化数据源");
        DruidDataSource dataSource = new DruidDataSource();

        List<Filter> list = new ArrayList<>();
        list.add(wallFilter());
        dataSource.setProxyFilters(list);

        return dataSource;
    }

    //删除菜单？  sys_menu
    //sys_role_menu
    @Bean
    public WallFilter wallFilter(){
        WallFilter filter = new WallFilter();

        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);//允许一个statment中执行多条sql语句

        filter.setConfig(config);
        return filter;
    }




}

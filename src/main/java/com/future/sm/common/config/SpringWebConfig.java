package com.future.sm.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * 通过此过滤器完成shiro的初始化操作
 */
@Configuration
public class SpringWebConfig {//取代web.xml中的filter配置
    //注册filter对象
    @Bean
    public FilterRegistrationBean newFilterRegistrationBean(){
        //1.构建过滤器的注册器对象
        FilterRegistrationBean filterRegistrationBean =
                new FilterRegistrationBean();
        //2.注册过滤器
        filterRegistrationBean.setFilter(
                new DelegatingFilterProxy("shiroFilterFactory")
        );
        //3.进行过滤器配置
        //配置过滤器的生命周期管理(可选)由ServletContext对象负责
        filterRegistrationBean.setEnabled(true);//默认就是true
        filterRegistrationBean.addUrlPatterns("/*");
        //...
        return filterRegistrationBean;

    }
}

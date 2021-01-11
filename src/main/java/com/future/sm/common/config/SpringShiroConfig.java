package com.future.sm.common.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class SpringShiroConfig {
    //配置SecurityManager对象
    /**@Bean 描述的方法,其返回值会交给spring管理
     * @Bean 一般应用在整合第三bean资源时*/
    @Bean
    public SecurityManager newSecurityManager(){
        DefaultSecurityManager securityManager =
                new DefaultWebSecurityManager();
        return securityManager;
    }

    //配置ShiroFilterFactoryBean对象
    //(通过此对象创建shiro中的过滤器对象并指定过滤规则)
    //创建SpringShiroFilter(shiroFilterFactory)
    @Bean("shiroFilterFactory")
    public ShiroFilterFactoryBean newShiroFilterFactoryBean(
            @Autowired SecurityManager securityManager
    ){
        ShiroFilterFactoryBean shiroFilterFactoryBean =
                new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录的url（如果没有登陆则先访问此页面）
        shiroFilterFactoryBean.setLoginUrl("/doLoginUI");

        //定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        //静态资源允许匿名访问:"anon"
        map.put("/bower_components/**","anon");
        map.put("/build/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        map.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}

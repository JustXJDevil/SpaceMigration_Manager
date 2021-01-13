package com.future.sm.common.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

@Configuration
public class SpringShiroConfig {
    //配置SecurityManager对象
    /**@Bean 描述的方法,其返回值会交给spring管理
     * @Bean 一般应用在整合第三bean资源时*/
    @Bean
    public SecurityManager newSecurityManager(
            @Autowired Realm realm,
            @Autowired CacheManager cacheManager,
            @Autowired CookieRememberMeManager newCookieRememberMeManager,
            @Autowired DefaultWebSessionManager newDefaultWebSessionManager){
        DefaultSecurityManager securityManager =
                new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(cacheManager);
        securityManager.setRememberMeManager(newCookieRememberMeManager);
        securityManager.setSessionManager(newDefaultWebSessionManager);
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
        //注意put的顺序，允许匿名访问的应该先put，不允许的放最后
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        //静态资源允许匿名访问:"anon"
        map.put("/bower_components/**","anon");
        map.put("/build/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        //允许匿名访问登录界面
        map.put("/user/doLogin","anon");
        map.put("/doLogout","logout");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        //map.put("/**","authc");
        map.put("/**","user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**==================授权配置=================**/

    /**
     * 配置shiro框架中一些bean对象的生命周期管理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
    {
        return new LifecycleBeanPostProcessor();
    }

    /**
     *配置代理对象创建器,通过此对象为目标业务对象创建代理对象
     * (新版本的springboot可以不配置)
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator()
    {
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 配置advisor对象,
     * shiro框架底层会通过此对象的matchs方法返回值
     * 决定是否创建代理对象,进行权限控制
     *
     * 此对象会对切入点、通知等对象进行相关描述，后续
     * DefaultAdvisorAutoProxyCreator对象会基于描述，为目标创建代理对象
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(
            @Autowired SecurityManager securityManager
    ){
        AuthorizationAttributeSourceAdvisor advisor=
                new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    //配置shiro内置的cache，记得注入给securityManager
    @Bean
    public CacheManager newCacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public Cookie newCookie(){
        Cookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(60*60);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager newCookieRememberMeManager(
            @Autowired Cookie newCookie
    ){
        CookieRememberMeManager cookieRememberMeManager =
                new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(newCookie);
        return cookieRememberMeManager;
    }

    @Bean
    public DefaultWebSessionManager newDefaultWebSessionManager(){
        DefaultWebSessionManager defaultWebSessionManager =
                new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(60*60*1000);
        return defaultWebSessionManager;
    }
}

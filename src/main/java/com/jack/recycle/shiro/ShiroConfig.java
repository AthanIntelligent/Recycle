package com.jack.recycle.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public AccountRealm accoutRealm(){
        return new AccountRealm();
    }

    /*
     Realm 对象已经注入到IOC容器中了，所以，Realm 注入到 DefaultWebSecurityManager 时，
     直接在 IOC 容器中拿就行，使用@Qualifier("accoutRealm")注解定位到对应的Bean
     一般来说，Bean名就是方法名，也可以自己指定@Bean(name="")
    */
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("accoutRealm") AccountRealm accountRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // Realm 注入到 DefaultWebSecurityManager
        manager.setRealm(accountRealm);
        return manager;
    }

//     DefaultWebSecurityManager 注入到 ShiroFilterFactoryBean 的过程与上面的一样
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap=new LinkedHashMap<>(8);
//        filterMap.put("jwt",jwtFilterRegBean.getFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

        return factoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        //这些请求不通过jwtFilter
        chainDefinition.addPathDefinition("/login","anon");
        chainDefinition.addPathDefinition("/login.html","anon");
        chainDefinition.addPathDefinition("/unauthorized","anon");
        // 所有请求通过我们自己的JWT Filter
        chainDefinition.addPathDefinition("/**","user");
        return chainDefinition;
    }


//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
//        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
//        //登录 不需要认证
//        chain.addPathDefinition("/login","anon");
////        //登录页面 不需要认证
////        chain.addPathDefinition("/loginView","anon");
////        //read权限才能访问
////        chain.addPathDefinition("/userRead","perms[read]");
////        //write权限才能访问
////        chain.addPathDefinition("/userWrite","perms[write]");
////        //登出
////        chain.addPathDefinition("/logout","logout");
//        // 所有uri 需要登录
////        chain.addPathDefinition("/**","user");
//
//        return chain;
//    }

}

package cn.sea.config;

import cn.sea.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro框架相关的配置类
 *  只要我们创建了安全管理器并将其交由Spring容器管理，
 *  那么shiro就会自动给安全管理器工具类注入安全管理器，
 *  所以我们可以直接获取主体（subject）
 */

@Configuration
public class ShiroConfig {

    // 1.创建shiroFilter 负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 配置系统的受限资源 和 配置系统的公共资源
        Map<String, String> map = new HashMap<>();
        map.put("/user/login", "anon"); // 设置 /user/login 为公共资源, 受限资源应该放在公共资源之后
        map.put("/register.jsp", "anon"); // 放行
        map.put("/user/register", "anon"); // 放行
        //map.put("/index.jsp", "authc"); // key：资源路径   值：authc-->表示请求的这个资源需要认证和授权
        map.put("/**", "authc"); // /** 表示所有资源都需要认证，除了使用 setLoginUrl()设置的认证界面



        // 默认认证界面路径 /login.jsp
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        // 配置认证和授权规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    // 2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    // 3.创建自定义的realm
    @Bean
    public Realm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();
        // 1.修改默认的凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 1.1 设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 1.2 设置hash散列次数
        credentialsMatcher.setHashIterations(1024);
        // 1.3 为自定义 realm 设置凭证器
        customerRealm.setCredentialsMatcher(credentialsMatcher);

        // 开启缓存管理
        customerRealm.setCacheManager(new EhCacheManager());
        customerRealm.setCachingEnabled(true); // 开启全局的缓存管理
        customerRealm.setAuthenticationCachingEnabled(true); // 开启认证的缓存
        customerRealm.setAuthenticationCacheName("authenticationCache"); // 设置认证缓存的名字
        customerRealm.setAuthorizationCachingEnabled(true); // 开启授权的缓存
        customerRealm.setAuthorizationCacheName("authorizationCache"); // 设置授权缓存的名字
     return customerRealm;
    }

}

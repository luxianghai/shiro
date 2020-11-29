package cn.sea;

import cn.sea.realm.CustomerMd5Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * 测试 CustomerMd5Realm
 */

public class TestCustomerMd5RealmAuthenticator {

    public static void main(String[] args) {

        // 创建安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 创建指定 realm 对象
        CustomerMd5Realm realm = new CustomerMd5Realm();
        // 设置自定义realm使用hash凭证匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        realm.setCredentialsMatcher(hashedCredentialsMatcher); // 使用 hash 凭证匹配器
        hashedCredentialsMatcher.setHashAlgorithmName("md5"); // 使用 md5 算法加密
        hashedCredentialsMatcher.setHashIterations(1024); // 设置散列的次数
        // 为安全管理器设置 realm
        securityManager.setRealm(realm);
        // 将安全管理器注入安全管理器工具类中
        SecurityUtils.setSecurityManager(securityManager);
        // 通过安全管理器工具类获取subject
        Subject subject = SecurityUtils.getSubject();
        // 生成令牌
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen", "123");
        try {
            // 认证
            subject.login(token);
            System.out.println("登录成功 ~" );
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误 ~");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误 ~");
        }


        // 认证用户进行授权
        if ( subject.isAuthenticated() ) {// 认证成功

            // 一：基于角色的访问控制权限
            // 1. 基于单角色的权限控制
            System.out.println( "单角色 --> " + subject.hasRole("user"));
            // 2. 基于多角色的权限控制
            System.out.println( "多角色 --> " + subject.hasAllRoles(Arrays.asList("admin", "user")));

            // 3. 是否具有其中一个角色
            boolean[] booleans = subject.hasRoles(Arrays.asList("admin", "user","supper"));
            for ( boolean b: booleans) {
                System.out.println("any：" + b);
            }

            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            // 二、基于资源的访问控制   权限字符串 --> 资源标识符:操作:资源类型
            System.out.println("权限："+subject.isPermitted("user:update:01")); // 是否具有操作user的所有权限
            System.out.println("权限："+subject.isPermitted("product:update")); // 是否具有更新product的所有权限
            System.out.println("权限："+subject.isPermitted("product:create")); // 是否具有创建product的所有权限
            // 分别具有哪些权限
            boolean[] permitted = subject.isPermitted("user:*:01", "order:*:10");
            for (boolean b : permitted) {
                System.out.println("any permitteds --> " + b);
            }

            // 同时具有哪些权限
            boolean permittedAll = subject.isPermittedAll("user:*:01", "order:*:10");
            System.out.println("permittedAll = " + permittedAll);
        }
    }
}

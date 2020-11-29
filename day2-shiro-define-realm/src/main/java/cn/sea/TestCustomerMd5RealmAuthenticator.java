package cn.sea;

import cn.sea.realm.CustomerMd5Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

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
    }
}

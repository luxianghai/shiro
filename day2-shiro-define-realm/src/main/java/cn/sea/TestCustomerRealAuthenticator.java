package cn.sea;

import cn.sea.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * 使用自定义 realm
 */

public class TestCustomerRealAuthenticator {

    public static void main(String[] args) {
        // 创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 设置自定义 realm
        securityManager.setRealm(new CustomerRealm());
        // 将安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        // 通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();
        // 创建 token
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen", "123123");

        try {
            subject.login(token);
            System.out.println("认证状态："+subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败：用户名错误 ~");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败：密码错误 ~");
        }
    }
}

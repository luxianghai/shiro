package cn.sea;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * 第一个shiro程序
 */

public class TestAuthenticator {

    public static void main(String[] args) {
        // 1. 创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        // 2. 给 安全管理器对象设置 realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));

        // 3. 给全局安全管理工具设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);

        // 4. 获取当前主体对象
        Subject subject = SecurityUtils.getSubject();

        // 5. 创建令牌（身份信息+凭证信息）
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen", "123");

        try {
            // 用户认证(认证成功没有返回值，认证失败则抛出异常)
            subject.login(token);
            System.out.println("认证状态："+subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败，用户名不存在 ~ ");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败，密码错误 ~ ");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("认证失败：未知异常！！");
        }
    }
}

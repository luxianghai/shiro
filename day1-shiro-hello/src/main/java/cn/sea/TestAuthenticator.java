package cn.sea;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class TestAuthenticator {

    public static void main(String[] args) {

        // 1. 创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        // 2. 给安全管理器设置realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));

        // 3. 给全局安全工具设置安全管理器   (SecurityUtils 全局安全工具类)
        SecurityUtils.setSecurityManager(securityManager);

        // 4. 获取当前主体对象  (关键对象 Subject  主体)
        Subject subject = SecurityUtils.getSubject();

        // 5. 创建令牌（身份信息+凭证信息）
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen","123");

        try {
            // 用户认证(认证成功没有返回值，认证失败则抛出异常)
            System.out.println("认证状态："+subject.isAuthenticated());
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

        Realm realm;

    }
}

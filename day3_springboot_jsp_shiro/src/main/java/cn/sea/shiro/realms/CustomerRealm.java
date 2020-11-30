package cn.sea.shiro.realms;

import cn.sea.entity.User;
import cn.sea.service.UserService;
import cn.sea.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

/**
 * 自定义realm
 */
public class CustomerRealm extends AuthorizingRealm {

    // 处理授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // 获取身份信息
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        System.out.println("调用授权验证："+primaryPrincipal);
        // 根据主身份信息获取角色 和 权限信息
        if ( "xiaochen".equals(primaryPrincipal)) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            // 添加权限角色
            simpleAuthorizationInfo.addRole("user");
            simpleAuthorizationInfo.addRole("admin");
            // 添加资源操作权限
            //simpleAuthorizationInfo.addStringPermission("user:*:*");// 对用户模块具有所有的操作权限
            simpleAuthorizationInfo.addStringPermission("user:update:*"); // 更新权限
            simpleAuthorizationInfo.addStringPermission("user:find:*"); // 查询权限
            simpleAuthorizationInfo.addStringPermission("user:delete:"+1);
            return simpleAuthorizationInfo;
        }
        return null;
    }

    // 处理认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("=================================");
        // 1.获取身份信息
        String principal = (String) token.getPrincipal();
        // 2.根据身份信息查询数据库
        // 2.1 在工厂中获取service对象,获取 UserServiceImpl
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
        User user = userService.findByUsername(principal);

        if( !ObjectUtils.isEmpty(user) ) { // 如果user不为null
            // 参数1：身份信息 参数2：凭证信息 参数3：随机验  参数4：当前realm的名字
            return new SimpleAuthenticationInfo(principal, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
        }

        return null;

       /* if ( "xiaochen".equals(principal) ) {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("xiaochen","123",this.getName());
            return authenticationInfo;
        }*/
    }
}

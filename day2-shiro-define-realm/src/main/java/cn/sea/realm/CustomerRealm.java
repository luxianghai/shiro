package cn.sea.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm实现
 * 目的：从数据库中获取 认证/授权 的数据源
 */

public class CustomerRealm extends AuthorizingRealm {
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 在token中获取用户的身份信息（我这里的身份信息是用户名）
        String principal = (String) token.getPrincipal();
        System.out.println("principal = " + principal);

        // 根据身份信息使用 jdbc/mybatis 查询相关数据
        if ( "xiaochen".equals(principal) ) {
            // 参数1：返回数据库中的正确用户名 参数2：返回数据库中的正确密码  参数3：提供当前realm的名字，this.getName
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal,"123123",this.getName());
            return authenticationInfo;
        }
        return null;
    }
}

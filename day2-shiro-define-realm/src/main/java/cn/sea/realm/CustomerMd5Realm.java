package cn.sea.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 使用自定义 realm，并加入 md5 + salt + hash散列
 */

public class CustomerMd5Realm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取身份信息
        String principal = (String) token.getPrincipal();
        // 根据用户名查询数据库
        if ( "xiaochen".equals(principal) ) {
            // 参数1：从数据库中查询到的用户名  参数2：从数据库中查询到的密码(md5+salt的密码)
            // 参数3：注册时的随机盐      参数4：realm的名字
            return new SimpleAuthenticationInfo(principal, "bc5203d5ed09b5fdbb511b2252d945d1",
                    ByteSource.Util.bytes("X@%1"),
                    this.getName());
        }
        return null;
    }
}

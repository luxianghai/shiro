package cn.sea.shiro.realms;

import cn.sea.entity.Role;
import cn.sea.entity.User;
import cn.sea.service.RoleService;
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
import org.springframework.util.CollectionUtils;
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
        // 1. 根据主身份信息获取角色 和 权限信息
        // 1.1 从工厂中拿到 UserService 和 RoleService
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
        RoleService roleService = (RoleService) ApplicationContextUtils.getBean("roleServiceImpl");
        // 1.2 调用 UserService 根据用户名（唯一标识）查询用户的角色信息
        User user  = userService.findRolesByUsername(primaryPrincipal);

        if ( !CollectionUtils.isEmpty(user.getRoles()) ) { // 如果 roles 集合不为空
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            // 授权角色信息
            user.getRoles().forEach( role -> {
                simpleAuthorizationInfo.addRole(role.getRolename());
                System.out.println( "role ---> " +  role);
                // 授权权限信息
                //  调用 RoleService 根据用户名（唯一标识）查询用户的角色信息
                Role permsByRoleId = roleService.findPermsByRoleId(role.getId());
                if ( !CollectionUtils.isEmpty(permsByRoleId.getPermissions())) { // 如果权限信息不为空
                    permsByRoleId.getPermissions().forEach( perm -> {
                        simpleAuthorizationInfo.addStringPermission(perm.getPermname()); // 赋予权限信息
                    });
                }
            });

            return simpleAuthorizationInfo;
        }

        return null;

        /*if ( "xiaochen".equals(primaryPrincipal)) {
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
        }*/
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

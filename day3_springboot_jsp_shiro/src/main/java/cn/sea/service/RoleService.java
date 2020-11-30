package cn.sea.service;

import cn.sea.entity.Role;

public interface RoleService {

    // 根据角色id查询权限信息集合
    Role findPermsByRoleId(String id) ;
}

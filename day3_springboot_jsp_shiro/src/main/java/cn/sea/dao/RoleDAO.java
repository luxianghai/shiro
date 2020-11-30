package cn.sea.dao;

import cn.sea.entity.Role;
import org.springframework.stereotype.Service;



@Service
public interface RoleDAO {

    // 根据角色id查询权限信息集合
    Role findPermsByRoleId(String id) ;

}

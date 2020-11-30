package cn.sea.service.impl;

import cn.sea.dao.RoleDAO;
import cn.sea.entity.Role;
import cn.sea.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public cn.sea.entity.Role findPermsByRoleId(String id) {

        try {
            Role role = roleDAO.findPermsByRoleId(id);
            if (ObjectUtils.isEmpty(role) || CollectionUtils.isEmpty(role.getPermissions()) ) {
                throw new RuntimeException("角色权限信息查询失败");
            }
            return role;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("角色权限信息查询失败");
        }
    }
}

package cn.sea;

import cn.sea.entity.Role;
import cn.sea.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class TestRoleService {

    @Autowired
    private RoleService roleService;

    @Test
    public void findPermsByRoleId() {
        Role role = roleService.findPermsByRoleId("1");
        System.out.println(role);
    }
}

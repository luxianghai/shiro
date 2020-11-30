package cn.sea.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "订单控制器OrderController")
@Controller
@RequestMapping("/order")
public class OrderController {

    @ApiOperation("添加订单")
    @PostMapping("/save")
    //@RequiresRoles({"admin","user"}) // 用于判断哪些角色能访问该方法,写多个角色时代表同时具有才能访问
    @RequiresPermissions({"user:*:*"}) // 权限控制字符串
    public String save() {

        System.out.println("进入 save 方法 . . .");
        // 1. 获取主体对象
        Subject subject = SecurityUtils.getSubject();

        // 代码形式进行授权
        /*if( subject.hasRole("admin") ) {// 是否具有admin角色
            System.out.println("保存订单 ~");
        } else {
            System.out.println("无权访问 ~");
        }*/


        return "rediect:/index.jsp";
    }
}

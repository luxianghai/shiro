package cn.sea.controller;

import cn.sea.entity.User;
import cn.sea.service.UserService;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api("用户控制器UserController")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public String register(@ApiParam("传入一个用户实体") User user) {
        try {
            userService.register(user);
            return "redirect:/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register.jsp";
        }
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout(); // 退出登录
        return "redirect:/login.jsp";
    }

    @ApiOperation("用来处理身份认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", type = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", type = "String", required = true)
    })
    @PostMapping("/login")
    public String login(String username, String password) {

        // 获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {
            // 生成令牌并进行认证
            subject.login(new UsernamePasswordToken(username,password));
            return "redirect:/index.jsp"; // 认证成功则跳转到 index.jsp 页面
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误 ~");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码名错误 ~");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("未知错误，认证失败 ~");
        }
        return "redirect:/login.jsp"; // 认证失败，跳转到 login.jsp 页面
    }
}

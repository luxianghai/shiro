package cn.sea.controller;

import cn.sea.entity.User;
import cn.sea.service.UserService;
import cn.sea.utils.ValidateImageCodeUtils;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Api("用户控制器UserController")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("验证码接口")
    @GetMapping("/getImage")
    public void getImage(@ApiIgnore HttpSession session, @ApiIgnore HttpServletResponse response) throws IOException {
        // 创建验证码对象
        ValidateImageCodeUtils validateImageCodeUtils = new ValidateImageCodeUtils();
        // 获取验证码图片
        BufferedImage image = validateImageCodeUtils.getImage(4);
        // 获取验证码
        String code = validateImageCodeUtils.getText();
        // 将验证码存入session域中
        session.setAttribute("code", code);
        // 设置相应类型
        response.setContentType("image/png");
        // 将验证码图片响应回客户端
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

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

    @ApiOperation("用来处理身份认证，用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", type = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", type = "String", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", type = "String", required = true),
    })
    @PostMapping("/login")
    public String login(String username, String password, String code, @ApiIgnore HttpSession session) {

        String code1 = (String) session.getAttribute("code");

        try {
            if ( code1.equalsIgnoreCase(code) ) {
                // 获取主体对象
                Subject subject = SecurityUtils.getSubject();
                // 生成令牌并进行认证
                subject.login(new UsernamePasswordToken(username,password));
                return "redirect:/index.jsp"; // 认证成功则跳转到 index.jsp 页面
            }
            System.out.println("验证码错误 ~ ~ ~ . . . .");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误 ~");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码名错误 ~");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("未知错误，认证失败 ~\n"+ e.getMessage());
        }
        return "redirect:/login.jsp"; // 认证失败，跳转到 login.jsp 页面
    }
}

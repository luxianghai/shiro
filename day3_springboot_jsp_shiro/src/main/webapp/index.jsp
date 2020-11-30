<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>主页</title>
</head>
<body>
<div>
    <h1>系统主页 1.0</h1>
    <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
    <ul>
        <%-- 所有用户都能看到 用户管理 --%>
        <%--<li><a href="">用户管理</a></li>--%>
        <!-- 配置 user，admin 中的任何一个用户都能访问 -->
        <shiro:hasAnyRoles name="user,admin">
            <li><a href="">用户管理</a>
                <ul>
                    <!-- 添加权限 -->
                    <shiro:hasPermission name="user:add:*">
                        <li><a href="">添加</a></li>
                    </shiro:hasPermission>
                    <!-- 删除权限 -->
                    <shiro:hasPermission name="user:delete:*">
                        <li><a href="">删除</a></li>
                    </shiro:hasPermission>
                    <!-- 修改权限 -->
                    <shiro:hasPermission name="user:update:*">
                        <li><a href="">修改</a></li>
                    </shiro:hasPermission>
                    <!-- 查询用户权限 -->
                    <shiro:hasPermission name="user:find:*">
                        <li><a href="">查询用户</a></li>
                    </shiro:hasPermission>
                    <!-- 查询订单权限 -->
                    <shiro:hasPermission name="user:order:*">
                        <li><a href="">查询订单</a></li>
                    </shiro:hasPermission>
                    <!-- 公共权限 -->
                    <li><a href="">公共权限</a></li>

                </ul>
            </li>
        </shiro:hasAnyRoles>
        <%-- 配置只有admin用户才能看到的资源，非admin用户不能看到 --%>
        <shiro:hasRole name="admin">
            <li><a href="">商品管理</a></li>
            <li><a href="">订单管理</a></li>
            <li><a href="">物流管理</a></li>
        </shiro:hasRole>

    </ul>
</div>

</body>
</html>
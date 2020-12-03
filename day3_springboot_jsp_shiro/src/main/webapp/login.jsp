<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>用户登录</title>
</head>
<body>
<div>
    <h1>用户登录</h1>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        用户名：<input type="text" name="username" /> <br/>
        密码 ：<input type="text" name="password"> <br/>
        验证码：<input type="text" name="code"> <img src="${pageContext.request.contextPath}/user/getImage" /> <br/>
        <input type="submit" value="登录" />
    </form>
</div>
</body>
</html>
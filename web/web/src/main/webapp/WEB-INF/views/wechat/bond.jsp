<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>绑定微信</title>
</head>
<body>
<form name="input" action="${website}wechat/bond" method="POST">
    手机号码:
    <input type="text" name="phone" />
    <br />密码:
    <input type="password" name="password" /> <input type="hidden" name="openId" value="${openId}" />
    <br /><input type="submit" value="绑定" />
</form>
</body>
</html>

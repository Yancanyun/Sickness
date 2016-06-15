<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>绑定微信</title>
</head>
<body>
<form name="input" action="${website}wechat/unbond" method="POST">
    确定要解绑该会员吗：
    <br/>
    姓名: ${vipInfo.name}
    <br/>
    手机号码: ${vipInfo.phone}
    <input type="hidden" name="openId" value="${openId}" />
    <br /><input type="submit" value="解绑" />
</form>
</body>
</html>

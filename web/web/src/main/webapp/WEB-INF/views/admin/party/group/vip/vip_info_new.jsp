<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/28
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加会员基本信息</title>
</head>
<body>
<form action="${website}admin/party/group/vip/new" method="post">
  姓名：<input name="name"/><br>
  性别：<input type="radio" value="0" name="sex" checked>未说明
  <input type="radio" value="1" name="sex">男
  <input type="radio" value="2" name="sex">女<br>
  电话：<input name="phone"/><br>
  生日：<input name="birthday"/><br>
  QQ：<input name="qq"/><br>
  Email：<input name="email"/><br>
</form>
</body>
</html>

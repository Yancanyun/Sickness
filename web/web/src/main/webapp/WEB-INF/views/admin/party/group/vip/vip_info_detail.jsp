<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/28
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员基本信息详情</title>
</head>
<body>
<form action="${website}admin/party/group/vip/new" method="post">
  姓名：<input name="name" value="${vipInfo.name}"/><br>
  性别：${vipInfo.sex}
  <input type="radio" value="0" name="sex" checked>未说明
  <input type="radio" value="1" name="sex">男
  <input type="radio" value="2" name="sex">女<br>
  电话：<input name="phone" value="${vipInfo.phone}"/><br>
  生日：<input name="birthday" value="${vipInfo.birthday}"/><br>
  QQ：<input name="qq" value="${vipInfo.qq}"/><br>
  Email：<input name="email" value="${vipInfo.email}"/><br>
  <button type="submit">提交</button>
  <bttton type="reset">重置</bttton>
  <a href="${website}admin/party/group/vip/list">返回</a>
</form>
</body>
</html>

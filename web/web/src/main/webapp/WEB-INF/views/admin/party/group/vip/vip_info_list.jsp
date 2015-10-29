<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/28
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>会员基本信息列表</title>
</head>
<body>
<form action="${website}admin/party/group/vip/ajax/search/{curPage}" method="post">
  关键字：<input name="keyword"/><br>
  <button type="submit">搜索</button><br>
</form>
<a href="${website}admin/party/group/vip/new">添加会员</a><br>
</body>
</html>

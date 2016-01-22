<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/21
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
当前等级为：${gradeName}
<c:forEach items="${vipIntegralDtoList}" var="vipIntegralDto">

</c:forEach>
</body>
</html>

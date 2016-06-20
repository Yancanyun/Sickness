<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>修改会员信息</title>
</head>
<body>
<form action="${website}wechat/vipinfo" method="POST">
    姓名:
    <input type="text" name="name" value="${vipInfo.name}"/>
    <br/>
    性别:
    <label>
        <input type="radio" value="1" name="sex" <c:if test="${vipInfo.sex==1}">checked="checked"</c:if>>男
    </label>
    <label>
        <input type="radio" value="2" name="sex" <c:if test="${vipInfo.sex==2}">checked="checked"</c:if>>女
    </label>
    <label>
        <input type="radio" value="0" name="sex" <c:if test="${vipInfo.sex==0}">checked="checked"</c:if>>未说明
    </label>
    <br/>
    生日:
    <input type="text" name="birthday" value="${birthday}"/>
    <br/>
    QQ:
    <input type="text" name="qq" value="${vipInfo.qq}"/>
    <br/>
    邮箱:
    <input type="text" name="email" value="${vipInfo.email}"/>
    <br/>
    <input type="hidden" name="openId" value="${openId}" /> <input type="submit" value="提交" />
</form>
</body>
</html>

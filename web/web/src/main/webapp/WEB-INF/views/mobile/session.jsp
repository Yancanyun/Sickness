<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset='utf-8'/>
    <title>Session过期</title>
    <jsp:include page="/WEB-INF/views/mobile/common/head.jsp" />
    <link rel="stylesheet" type="text/css" href="${staticWebsite}css/mobile/error-scan.css">
</head>
<body>
<div class="page">
    <div class="error-img">
        <img src="${staticWebsite}img/mobile/error/scan.png" />
    </div>
    <p>你的Session已过期，请重新扫描二维码</p>
</div>
</body>
</html>
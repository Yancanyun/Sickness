<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
	<meta charset='utf-8'/>
	<title>404</title>
	<jsp:include page="/WEB-INF/views/mobile/common/head.jsp" />
	<link rel="stylesheet" type="text/css" href="${staticWebsite}css/mobile/404.css">
</head>
<body>
<div class="page">
	<div class="error-img">
		<img src="${staticWebsite}img/mobile/error/call.jpg">
	</div>
	<div class="error-note clearfix">
		<p class="note clearfix">404：</p>
		<ul class="reason">
			<li>我们无法找到您所请求的页面！</li>
		</ul>
	</div>
	<div class="link">
		<%--<a href="javascript:;"><i class="fa fa-refresh"></i>&nbsp刷新</a>--%>
		<a href="${website}mobile/dish/image"><i class="fa fa-angle-left"></i>&nbsp返回首页</a>
	</div>
</div>
</body>
</html>
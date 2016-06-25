<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
	<meta charset='utf-8'/>
	<title>500</title>
	<jsp:include page="/WEB-INF/views/mobile/common/head.jsp" />
	<link rel="stylesheet" type="text/css" href="${staticWebsite}css/mobile/500.css">
</head>
<body>
<div class="page">
	<div class="error-img">
		<img src="${staticWebsite}img/mobile/error/call.jpg">
	</div>
	<div class="error-note clearfix">
		<p class="note clearfix">500：</p>
		<ul class="reason">
			<li>您所请求的网页出现了些问题！</li>
			<li>${eMsg}</li>
		</ul>
	</div>
	<div class="link">
		<a href="javascript:location.reload();"><i class="fa fa-refresh"></i>&nbsp刷新</a>
		<a href="${website}mobile/dish/image"><i class="fa fa-angle-left"></i>&nbsp返回首页</a>
	</div>
</div>
</body>
</html>
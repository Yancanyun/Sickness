<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en">
	<head>
		<meta charset='utf-8'/>
		<title>开台</title>
        <jsp:include page="/WEB-INF/views/mobile/common/head.jsp" />
		<link rel="stylesheet" type="text/css" href="${staticWebsite}css/mobile/error-call.css">
	</head>
	<body>
		<div class="page">
			<div class="error-img">
				<img src="${staticWebsite}img/mobile/error/call.jpg" />
			</div>
			<p>该餐台还未开台，请联系服务员开台</p>
		</div>
	</body>
</html>


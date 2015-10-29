<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
<head>
    <title>404</title>
    <jsp:include page="WEB-INF/views/public/common/head.jsp"/>
    <link rel="stylesheet" type="text/css" href="${staticWebsite}css/common/error/error.css">
</head>
<body>
<div class="page page-text">
    <div class="container">
        <div class="row">
            <div class="col-sm-12 text-center">
                <h1 class="rotate">404!</h1>
						<span class="tip">
							<i class="fa fa-smile-o"></i>&nbsp;我们无法找到您所请求的页面！<br>
							请点击这里返回 <a href="${website}">首页</a>，或者继续浏览其他页面。</span>
            </div>
        </div>
    </div>
</div>
<div class="footer pull-right">
    <ul class="list-unstyled list-inline pull-left">
        <li>© &nbsp;2015&nbsp;北京广德腾建博曼科技有限公司 版权所有</li>
        <li>客服联系方式 电话:010-66886688 010-88668866 QQ:6666666 88888888</li>
    </ul>
</div>
</body>
</html>
<%
    String url = request.getAttribute("javax.servlet.error.request_uri") +
            (request.getQueryString() == null ? "" : ("?" + request.getQueryString()));
    String referer = request.getHeader("Referer");
    String ip = com.pandawork.core.common.util.IpUtil.getClientIP(request);
    String requestMethod = request.getMethod();
    String message = "404|" + url + "|" + referer + "|" + ip + "|" + requestMethod;
    com.pandawork.core.common.log.LogClerk.monitorLog.debug(message);
%>

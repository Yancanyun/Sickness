<%--
  Created by IntelliJ IDEA.
  User: quanyibo
  Date: 2016/6/7
  Time: 8:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="mobile">
  <tiles:putAttribute name="title" value="${webTitle}"/>
 <%-- <tiles:putAttribute name="header" value="/WEB-INF/views/mobile/dish/detail/header/detail_header.jsp"/>--%>
  <tiles:putAttribute name="body" value="/WEB-INF/views/mobile/order/body/order_list_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/mobile/order/inc/order_list_script.jsp"/>
  <tiles:putAttribute name="extendHead" value="<link rel='stylesheet' href='${staticWebsite}css/mobile/order.css'>"/>
</tiles:insertDefinition>


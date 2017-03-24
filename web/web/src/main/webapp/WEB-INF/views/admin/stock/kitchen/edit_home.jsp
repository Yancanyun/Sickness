<%--
  Created by IntelliJ IDEA.
  User: sanqi
  Date: 2017/3/22
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="admin">
    <tiles:putAttribute name="title" value="${webTitle}"/>
    <tiles:putAttribute name="body" value="/WEB-INF/views/admin/stock/kitchen/body/edit_body.jsp"/>
    <tiles:putAttribute name="script" value="/WEB-INF/views/admin/stock/kitchen/inc/edit_script.jsp"/>
</tiles:insertDefinition>

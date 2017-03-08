<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="admin">
    <tiles:putAttribute name="title" value="${webTitle}"/>
    <tiles:putAttribute name="body" value="/WEB-INF/views/admin/stock/kitchen/body/list_body.jsp"/>
    <tiles:putAttribute name="script" value="/WEB-INF/views/admin/stock/kitchen/inc/list_script.jsp"/>
</tiles:insertDefinition>

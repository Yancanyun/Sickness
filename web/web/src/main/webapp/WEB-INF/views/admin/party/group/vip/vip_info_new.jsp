<%--
  User: chenyuting
  Time: 2015/10/28 14:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/party/group/vip/body/new_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/party/group/vip/inc/new_script.jsp"/>
</tiles:insertDefinition>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 17/3/8
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/stock/specifications/body/edit_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/stock/specifications/inc/edit_script.jsp"/>
</tiles:insertDefinition>
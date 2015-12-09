<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/party/group/employee/body/new_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/party/group/employee/inc/new_script.jsp"/>
  <tiles:putAttribute name="extendHead" value="<link rel='stylesheet' href='${staticWebsite}css/admin/user-management/user-management.css'>"/>
</tiles:insertDefinition>


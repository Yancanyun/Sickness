<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/restaurant/service/body/call_management_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/restaurant/service/inc/call_management_script.jsp"/>
  <tiles:putAttribute name="extendHead"
                      value="<link rel='stylesheet' type='text/css' href='${staticWebsite}css/admin/restaurant-management/restaurant-management.css'>" />
</tiles:insertDefinition>
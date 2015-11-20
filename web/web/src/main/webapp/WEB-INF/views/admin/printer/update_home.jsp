<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/printer/body/update_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/printer/inc/update_script.jsp"/>
  <tiles:putAttribute name="extendHead"
                      value="<link rel='stylesheet' type='text/css' href='${staticWebsite}css/admin/base-info-management/printer-management.css'>" />
</tiles:insertDefinition>
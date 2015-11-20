<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
    <tiles:putAttribute name="title" value="${webTitle}"/>
    <tiles:putAttribute name="body" value="/WEB-INF/views/admin/restaurant/meal/period/body/list_body.jsp"/>
    <tiles:putAttribute name="script" value="/WEB-INF/views/admin/restaurant/meal/period/inc/list_script.jsp"/>
    <tiles:putAttribute name="extendHead" value="<style type='text/css'>
.control-label{
  padding-top: 0;
  }
  </style>"/>
</tiles:insertDefinition>
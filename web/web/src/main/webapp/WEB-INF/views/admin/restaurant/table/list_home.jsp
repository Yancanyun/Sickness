<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/restaurant/table/body/list_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/restaurant/table/inc/list_script.jsp"/>
  <tiles:putAttribute name="extendHead" value="<style type='text/css'>.J_edit,.J_change,.J_del{display:inline-block; width:56px;}</style>"/>
</tiles:insertDefinition>
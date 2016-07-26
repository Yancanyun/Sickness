<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/party/group/vip/body/consumption_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/party/group/vip/inc/consumption_script.jsp"/>
</tiles:insertDefinition>

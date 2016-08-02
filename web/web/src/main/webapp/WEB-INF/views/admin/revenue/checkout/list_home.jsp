<%--
  Created by IntelliJ IDEA.
  User: open1999
  Date: 2016/8/1
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/revenue/body/list_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/revenue/inc/list_script.jsp"/>
  <tiles:putAttribute name="extendHead"
                      value="<link rel='stylesheet' type='text/css' href='${staticWebsite}css/admin/business-analysis/business-analysis.css'>" />
</tiles:insertDefinition>


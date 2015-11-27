<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/27
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="admin">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/admin/vip/price/body/plan_list_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/admin/vip/price/inc/plan_list_script.jsp"/>
</tiles:insertDefinition>
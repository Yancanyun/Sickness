<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="mobile">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="header" value="/WEB-INF/views/mobile/dish/image/header/list_header.jsp"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/mobile/dish/image/body/list_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/mobile/dish/image/inc/list_script.jsp"/>
  <tiles:putAttribute name="extendHead" value="<link rel='stylesheet' href='${staticWebsite}css/mobile/classify-img.css'>
                                                <link rel='stylesheet' href='${staticWebsite}tool/pui2/assets/carousel/css/style.css'>"/>
</tiles:insertDefinition>
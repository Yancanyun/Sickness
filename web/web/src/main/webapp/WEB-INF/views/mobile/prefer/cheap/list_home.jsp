<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="mobile">
  <tiles:putAttribute name="title" value="${webTitle}"/>
  <tiles:putAttribute name="body" value="/WEB-INF/views/mobile/prefer/cheap/body/list_body.jsp"/>
  <tiles:putAttribute name="script" value="/WEB-INF/views/mobile/prefer/cheap/inc/list_script.jsp"/>
  <tiles:putAttribute name="extendHead" value="<link rel='stylesheet' href='${staticWebsite}css/mobile/recommend.css'>"/>
</tiles:insertDefinition>
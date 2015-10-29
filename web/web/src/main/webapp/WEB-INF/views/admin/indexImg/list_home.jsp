<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
    <tiles:putAttribute name="title" value="${webTitle}"/>
    <tiles:putAttribute name="body" value="/WEB-INF/views/admin/party/security/permission/body/list_body.jsp"/>
    <tiles:putAttribute name="script" value="/WEB-INF/views/admin/party/security/permission/inc/list_script.jsp"/>
    <tiles:putAttribute name="extendHead"
                        value="<link rel='stylesheet' type='text/css' href='/resources/tool/webuploader/css/webuploader.css'>" />
</tiles:insertDefinition>
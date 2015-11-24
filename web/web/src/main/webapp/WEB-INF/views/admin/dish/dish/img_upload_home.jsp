<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="admin">
    <tiles:putAttribute name="title" value="${webTitle}"/>
    <tiles:putAttribute name="body" value="/WEB-INF/views/admin/dish/dish/body/img_upload_body.jsp"/>
    <tiles:putAttribute name="script" value="/WEB-INF/views/admin/dish/dish/inc/img_upload_script.jsp"/>
    <tiles:putAttribute name="extendHead"
                        value="<link rel='stylesheet' type='text/css' href='${staticWebsite}tool/webuploader/css/webuploader.css'>
                        		<link rel='stylesheet' type='text/css' href='${staticWebsite}css/admin/base-info-management/base-info-management.css'>" />
</tiles:insertDefinition>

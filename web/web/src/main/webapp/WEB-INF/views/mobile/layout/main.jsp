<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!doctype html>
<html>
<head>
    <title><tiles:insertAttribute name="title"/></title>
    <tiles:insertAttribute name="head"/>
    <tiles:insertAttribute name="extendHead"/>
</head>
<body>
    <div class="page">
        <tiles:insertAttribute name="header"/>
        <tiles:insertAttribute name="footer"/>
        <tiles:insertAttribute name="body"/>
    </div>
    <tiles:insertAttribute name="script"/>
</body>
</html>
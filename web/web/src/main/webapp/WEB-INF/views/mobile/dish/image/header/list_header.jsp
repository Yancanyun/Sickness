<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--这个是带有分类搜索的头部-->
<div class="header clearfix" id="header">
    <div class="classify-group J_classify" data-classify-id="111">
        <!--将分类刷到这里,方便分类菜单的排布-->
        <c:forEach var="tag" items="${tagList}">
            <div class="J_classifyInfo">
                <input type="hidden" name="classifyName" value="${tag.name}">
                <input type="hidden" name="classifyId" value="${tag.id}">
            </div>
        </c:forEach>
        <a href="javascript:;" class="classify-trigger">分类</a><i class="fa fa-angle-right J_classifyIcon classify-icon"></i>
    </div>
    <div class="search-group">
        <input type="text" placeholder="搜索..." class="search form-control"><i class="fa fa-search search-img"></i>
    </div>
    <div class="text-trigger">
        <a href="${website}mobile/dish/text">文</a>
    </div>
</div>

<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('module/ext, module/classify', function(){});
    });
</script>


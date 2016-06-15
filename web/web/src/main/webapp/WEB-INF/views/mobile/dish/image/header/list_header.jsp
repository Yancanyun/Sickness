<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--这个是带有分类搜索的头部-->
<div class="header clearfix" id="header">
    <div class="classify-group J_classify" data-classify-id="${classifyId}">
        <!--将分类刷到这里,方便分类菜单的排布-->
        <div class="J_classifyInfo">
            <input type="hidden" name="classifyName" value="全部分类">
        </div>
        <c:forEach var="tag" items="${tagList}">
            <div class="J_classifyInfo">
                <input type="hidden" name="classifyName" value="${tag.name}">
                <input type="hidden" name="classifyId" value="${tag.id}">
            </div>
        </c:forEach>
        <a href="javascript:;" class="classify-trigger">分类</a><i class="fa fa-angle-right J_classifyIcon classify-icon"></i>
    </div>
    <div class="search-group">
        <input type="text" placeholder="搜索..." class="search form-control J_searchInput" autocomplete="off" ><i class="fa fa-search search-img J_searchIcon"></i>
        <input type="hidden" class="J_keyword" name="keyword" value="${keyword}"/>
    </div>
    <div class="text-trigger">
    <c:choose>
        <c:when test="${classifyId ne null}">
            <a href="${website}mobile/dish/text?classifyId=${classifyId}">文</a>
        </c:when>
        <c:when test="${keyword ne null}">
            <a href="${website}mobile/dish/text?keyword=${keyword}">文</a>
        </c:when>
        <c:otherwise>
            <a href="${website}mobile/dish/text">文</a>
        </c:otherwise>
    </c:choose>
    </div>
</div>
<script type="text/template" id="tpl">
    <div class="classify-container J_classifyContainer">
        <div class="classify-list">{@each list as it}<a href="${website}mobile/dish/image" class="classify J_classifyCeil" data-classify-id="&{it.classifyId}">&{it.classifyName}</a>{@/each}
        </div>
    </div>;
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('module/ext, module/classify', function(){});
    });
</script>


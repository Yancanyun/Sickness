<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--这个是一般的搜索头部-->
<div class="header clearfix" id="header">
    <h1 class="header-title">历史消费</h1>
    <i class="fa fa-search search-icon J_searchIcon" data-url="xxxx?"></i>
    <input class="search-input J_searchInput" type="text" placeholder="请输入菜品名称...">
    <div class="hidden"></div>
</div>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('module/ext, module/search-list', function(){});
    });
</script>


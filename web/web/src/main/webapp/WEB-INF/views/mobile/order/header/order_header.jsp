<%--
  Created by IntelliJ IDEA.
  User: ALIENWARE
  Date: 2016/6/14
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="header clearfix" id="header">
  <h1 class="header-title">
    <label>我的订单 : </label>
    <span class="J_tableNumber">${tableName}</span>
  </h1>
  <i class="fa fa-search search-icon J_searchIcon" data-url="xxxx?"></i>
  <input class="search-input J_searchInput" type="text" placeholder="请输入菜品名称...">
  <div class="hidden"></div>
</div>

<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('module/ext, module/search-list', function(){});
  });
</script>

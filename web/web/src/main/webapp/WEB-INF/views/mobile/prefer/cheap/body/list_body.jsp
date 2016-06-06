<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!--这个是一般的搜索头部-->
<div class="header clearfix" id="header">
  <h1 class="header-title">今日特价</h1>
  <i class="fa fa-search search-icon J_searchIcon" data-url="xxxx?"></i>
  <input class="search-input J_searchInput" type="text" placeholder="请输入菜品名称...">
  <div class="hidden"></div>
</div>
<div class="container">
  <div id="wrapper" class="scroll">
    <!-- 菜品列表 -->
    <ul class="dish-list clearfix J_scroll">
    </ul>
  </div><!-- wrapper -->
</div><!-- container -->
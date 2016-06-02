<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!--这个是一般的搜索头部-->
<div class="header clearfix" id="header">
  <h1 class="header-title">销量排行</h1>
  <i class="fa fa-search search-icon J_searchIcon" data-url="xxxx?"></i>
  <input class="search-input J_searchInput" type="text" placeholder="请输入菜品名称...">
  <div class="hidden"></div>
</div>
<div class="container">
  <div id="wrapper" class="scroll">
    <!-- 菜品列表 -->
    <ul class="dish-list clearfix J_scroll">
      <li class="dish clearfix" data-dish-id="1">
        <a href="#" class="dish-img"><img src="/resources/img/mobile/classify-img/dish-img.gif" alt="菜品图片"></a>
        <p class="dish-name">黑胡椒牛排</p>
        <p class="dish-sales">销&nbsp;<span>148</span></p>
        <div class="add-dish J_addDishTrigger" data-dish-number="1">
          点餐
          <span class="dish-number J_dishNumber hidden"></span>
        </div>
        <div class="dish-info clearfix">
          <p class="dish-star">
            <i class="fa fa-star active"></i>
            <i class="fa fa-star active"></i>
            <i class="fa fa-star active"></i>
            <i class="fa fa-star active"></i>
            <i class="fa fa-star"></i>
          </p>
          <p class="dish-price-sale">
            <span class="dish-price">￥42</span>
            <span class="dish-sale">￥32</span>
          </p>
        </div>
      </li>
    </ul>
  </div><!-- wrapper -->
</div><!-- container -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- 后端套轮播的默认图片资源地址 -->
<input type="hidden" class="J_carouselDefaultImg" href="${website}img/mobile/common/default-img.png">
<div class="container">
  <div id="wrapper" class="scroll">
    <div id="carousel" class="carousel slide">
      <ol class="carousel-indicators">
        <li data-target="#carousel" data-slide-to="0" class="active"></li>
        <li data-target="#carousel" data-slide-to="1"></li>
        <li data-target="#carousel" data-slide-to="2"></li>
        <li data-target="#carousel" data-slide-to="3"></li>
      </ol>

      <c:if test="${classifyId eq null and keyword eq null}">
      <div class="carousel-inner" role="listbox">
        <div class="item active">
          <a href="${website}mobile/dish/detail/${todayCheapActive.dishId}"><img src="${tinyWebsite}${todayCheapActive.bigImgList[0].imgPath}" alt="今日特价"></a>
          <div class="carousel-caption">
            <p class="dish-title">今日特价</p>
            <p class="dish-name">${todayCheapActive.dishName}</p>
            <p>
              <span class="dish-price">￥${todayCheapActive.dishPrice}</span>
              <span class="dish-sale">￥${todayCheapActive.dishSalePrice}</span>
            </p>
            <%--<div class="add-dish J_addDishTrigger" data-dish-number="">--%>
              <%--点餐--%>
              <%--<span class="dish-number J_dishNumber hidden"></span>--%>
            <%--</div>--%>
            <img src="${staticWebsite}img/mobile/classify-img/empty-circle.gif" alt="" class="empty-circle">
          </div>
        </div>

          <div class="item">
            <a href="${website}mobile/dish/detail/${todayCheapSecond.dishId}"><img src="${tinyWebsite}${todayCheapSecond.bigImgList[0].imgPath}" alt="今日特价"></a>
            <div class="carousel-caption">
              <p class="dish-title">今日特价</p>
              <p class="dish-name">${todayCheapSecond.dishName}</p>
              <p>
                <span class="dish-price">￥${todayCheapSecond.dishPrice}</span>
                <span class="dish-sale">￥${todayCheapSecond.dishSalePrice}</span>
              </p>
              <%--<div class="add-dish J_addDishTrigger" data-dish-number="">--%>
                <%--点餐--%>
                <%--<span class="dish-number J_dishNumber hidden"></span>--%>
              <%--</div>--%>
              <img src="${staticWebsite}img/mobile/classify-img/empty-circle.gif" alt="" class="empty-circle">
            </div>
          </div>

        <c:forEach var="feature" items="${featureList}">
          <div class="item">
            <a href="${website}mobile/dish/detail/${feature.dishId}"><img src="${tinyWebsite}${feature.bigImgList[0].imgPath}" alt="本店特色"></a>
            <div class="carousel-caption">
              <p class="dish-title">本店特色</p>
              <p class="dish-name">${feature.dishName}</p>
              <p>
                <span class="dish-price">￥${feature.dishPrice}</span>
                <span class="dish-sale">￥${feature.dishSalePrice}</span>
              </p>
              <%--<div class="add-dish J_addDishTrigger" data-dish-number="">--%>
                <%--点餐--%>
                <%--<span class="dish-number J_dishNumber hidden"></span>--%>
              <%--</div>--%>
              <img src="${staticWebsite}img/mobile/classify-img/empty-circle.gif" alt="" class="empty-circle">
            </div>
          </div>
          </c:forEach>
      </div>
    </div>
    </c:if>

    <!--菜品列表-->
    <ul class="dish-list clearfix J_scroll">
    </ul>
  </div>
</div>
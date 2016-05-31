<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container">
  <div id="wrapper" class="scroll">
    <div id="carousel" class="carousel slide">
      <ol class="carousel-indicators">
        <li data-target="#carousel" data-slide-to="0" class="active"></li>
        <li data-target="#carousel" data-slide-to="1"></li>
        <li data-target="#carousel" data-slide-to="2"></li>
        <li data-target="#carousel" data-slide-to="3"></li>
      </ol>

      <div class="carousel-inner" role="listbox">
        <div class="item active">
          <a href="#"><img src="${tinyWebsite}${todayCheapActive.bigImgList[0].imgPath}" alt="今日特价"></a>
          <div class="carousel-caption">
            <p class="dish-title">今日特价</p>
            <p class="dish-name">${todayCheapActive.dishName}</p>
            <p>
              <span class="dish-price">￥${todayCheapActive.dishPrice}</span>
              <span class="dish-sale">￥${todayCheapActive.dishSalePrice}</span>
            </p>
            <div class="add-dish J_addDishTrigger" data-dish-number="">
              点餐
              <span class="dish-number J_dishNumber hidden"></span>
            </div>
            <img src="${staticWebsite}img/mobile/classify-img/empty-circle.gif" alt="" class="empty-circle">
          </div>
        </div>

          <div class="item">
            <a href="#"><img src="${tinyWebsite}${todayCheapSecond.bigImgList[0].imgPath}" alt="今日特价"></a>
            <div class="carousel-caption">
              <p class="dish-title">今日特价</p>
              <p class="dish-name">${todayCheapSecond.dishName}</p>
              <p>
                <span class="dish-price">￥${todayCheapSecond.dishPrice}</span>
                <span class="dish-sale">￥${todayCheapSecond.dishSalePrice}</span>
              </p>
              <div class="add-dish J_addDishTrigger" data-dish-number="">
                点餐
                <span class="dish-number J_dishNumber hidden"></span>
              </div>
              <img src="${staticWebsite}img/mobile/classify-img/empty-circle.gif" alt="" class="empty-circle">
            </div>
          </div>

        <c:forEach var="feature" items="${featureList}">
          <div class="item">
            <a href="#"><img src="${tinyWebsite}${feature.bigImgList[0].imgPath}" alt="本店特色"></a>
            <div class="carousel-caption">
              <p class="dish-title">本店特色</p>
              <p class="dish-name">${feature.dishName}</p>
              <p>
                <span class="dish-price">￥${feature.dishPrice}</span>
                <span class="dish-sale">￥${feature.dishSalePrice}</span>
              </p>
              <div class="add-dish J_addDishTrigger" data-dish-number="">
                点餐
                <span class="dish-number J_dishNumber hidden"></span>
              </div>
              <img src="${staticWebsite}img/mobile/classify-img/empty-circle.gif" alt="" class="empty-circle">
            </div>
          </div>
          </c:forEach>
      </div>
    </div>

    <!--菜品列表-->
    <ul class="dish-list clearfix J_scroll">
      <%--<c:forEach var="dishDto" items="${dishDtoList}">--%>
        <%--<li class="dish clearfix" data-dish-id="${dishDto.id}">--%>
          <%--<a href="#" class="dish-img"><img src="${tinyWebsite}${dishDto.smallImg.imgPath}" alt="${dishDto.name}"></a>--%>
          <%--<p class="dish-name">${dishDto.name}</p>--%>
          <%--<p class="dish-like"><i class="fa fa-thumbs-up"></i>&nbsp;<span class="dish-like-number">${dishDto.likeNums}</span></p>--%>
          <%--<div class="add-dish J_addDishTrigger" data-dish-number="1">--%>
            <%--点餐--%>
            <%--<span class="dish-number J_dishNumber hidden"></span>--%>
          <%--</div>--%>
          <%--<div class="dish-info clearfix">--%>
            <%--<p class="dish-star">--%>
              <%--<i class="fa fa-star active"></i>--%>
              <%--<i class="fa fa-star active"></i>--%>
              <%--<i class="fa fa-star active"></i>--%>
              <%--<i class="fa fa-star active"></i>--%>
              <%--<i class="fa fa-star"></i>--%>
            <%--</p>--%>
            <%--<p class="dish-price-sale">--%>
              <%--<span class="dish-price">￥${dishDto.price}</span>--%>
              <%--<span class="dish-sale">￥${dishDto.salePrice}</span>--%>
            <%--</p>--%>
          <%--</div>--%>
        <%--</li>--%>
      <%--</c:forEach>--%>
    </ul>
  </div>
</div>
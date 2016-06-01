<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/30
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
  <div id="carousel" class="carousel slide">
    <ol class="carousel-indicators">
      <c:forEach items="${dishDto.bigImgList}" varStatus="status">
        <li data-target="#carousel" data-slide-to="${status.count}"></li>
      </c:forEach>
    </ol>
    <div class="carousel-inner" role="listbox">
      <span class="tag-background"></span>
      <span class="dish-tag">${dishDto.name}</span>
        <c:forEach items="${dishDto.bigImgList}" var="bigImg" varStatus="status">
            <c:choose>
              <c:when test="${status.first}">
                <div class="item active">
                  <a href="#"><img src="${tinyWebsite}${bigImg.imgPath}" alt="菜品展示"></a>
                </div>
              </c:when>
              <c:otherwise>
                <div class="item">
                  <a href="#"><img src="${tinyWebsite}${bigImg.imgPath}" alt="菜品展示"></a>
                </div>
              </c:otherwise>
            </c:choose>
        </c:forEach>
      </div>
  </div>
  <div class="dish-title">
    <span class="dish-name">${dishDto.name}</span>
    <c:if test="${dishDto.price ne dishDto.salePrice}">
      <span class="dish-price">￥${dishDto.price}</span>
    </c:if>
    <span class="dish-sale">￥${dishDto.salePrice}</span>
  </div>
  <p class="intro">${dishDto.description}</p>
  <form action="" method="" class="J_form">
    <input type="hidden" name="id" value="">
    <div class="order-require">
      <label>上菜方式</label>
    </div>
    <div class="options J_type">
      <input type="hidden" name="type" value="">
      <span data-type="0"><i class="fa fa-circle-o"></i>即起</span>
      <span data-type="1"><i class="fa fa-circle-o"></i>叫起</span>
    </div>
    <div class="order-require">
      <label>口&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;味</label>
    </div>
    <div class="options J_taste">
      <input type="hidden" name="taste" value="">
      <c:forEach items="${dishDto.tasteList}" var="taste">
        <span data-type="${taste.id}"><i class="fa fa-circle-o"></i>${taste.name}</span>
      </c:forEach>
    </div>
    <div class="order-require">
      <label>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</label>
      <div class="set-number">
        <button type="button" class="J_minus"><i class="fa fa-minus"></i></button>
        <input type="tel" name="number" value="1">
        <button type="button" class="J_plus"><i class="fa fa-plus"></i></button>
      </div>
    </div>
    <div class="order-require">
      <label>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
      <div class="set-remarks J_remarks">
        <c:forEach items="${remarkDtoList}" var="remarkDto">
          <input name="remarkId" value="${remarkDto.remark.id}" hidden="hidden">
          <span>${remarkDto.remark.name}</span>
        </c:forEach>
        <textarea name="remarks" rows="2"></textarea>
        <button type="submit" class="J_submit">点菜</button>
      </div>
    </div>
  </form>
</div>

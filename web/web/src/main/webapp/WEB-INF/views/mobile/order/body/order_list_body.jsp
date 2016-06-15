<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
  <div id="wrapper" class="scroll">
    <div class="order-service">
      <form >
        <label>上菜方式 ：</label>
        <input class='J_restoreValue' type="hidden" value="即起">
        <input class="J_serviceWay" type="radio" value= '0' name="serviceWay" checked/><span>即起</span>
        <input class="J_serviceWay" type="radio" value='1' name="serviceWay"/><span>叫起</span>
        <a class="order-dish J_orderDish" href="#">点菜</a>
      </form>
    </div>
    <!-- 正在下单菜品列表 -->
    <ul class="ordering-list clearfix J_scroll" data-button-type="1">
      <c:if test="${not empty myOrderDto}">
        <c:forEach  var="dto" items="${myOrderDto}">
        <li class="ordering-dish clearfix J_swipeEvent" data-ordering-dish-id="${dto.orderDishCacheId}">
          <!-- 套页需套以下input -->
          <input type="hidden" name="dishId" value="${dto.dishId}">
          <input type="hidden" name="dishName" value="${dto.name}">
          <input type="hidden" name="dishPrice" value="${dto.salePrice}">
          <!-- 菜品的最终售价 -->
          <input type="hidden" name="dishUnit" value="${dto.unitName}">
          <input type="hidden" name="dishNumber" value="${dto.count}">
          <c:if test="${not empty dto.tasteList}">
            <c:forEach var="tasteList" items="${dto.tasteList}">
              <input type="hidden" name="dishTaste" value="${tasteList.name}">
            </c:forEach>
          </c:if>
          <input type="hidden" name="dishRemark" value="${dto.remark}">
          <img class="ordering-dish-img" src="${dto.imgPath}" alt="暂无菜品展示图片">
          <div class="ordering-dish-info">
            <p class="info-text">
              <span class="ordering-dish-name">${dto.name}</span>
              <!-- 后端控制原价没有改变时，不显示price span -->
              <c:choose>
                <c:when test="${dto.price eq dto.salePrice}">
                  <span class="ordering-dish-sale">${dto.salePrice}</span>
                </c:when>
                <c:otherwise>
                  <span class="ordering-dish-price">${dto.price}</span>
                  <span class="ordering-dish-sale">${dto.salePrice}</span>
                </c:otherwise>
              </c:choose>

            </p>
            <p class="ordering-number">
              <button class="J_redudeButton"><i class="fa fa-minus"></i></button>
              <c:choose>
                <c:when test="${not empty dto.count}">
                  <input class="ordering-dish-number" type="tel" value="${dto.count}" readonly>
                </c:when>
                <c:otherwise>
                  <input class="ordering-dish-number" type="tel" value="0" readonly>
                </c:otherwise>
              </c:choose>
              <button class="J_plusButton"><i class="fa fa-plus"></i></button>
            </p>
            <p class="ordering-remark-info J_remarks">${dto.remark}</p>
          </div>
          <button class="J_delete">删除</button>
        </li>
        </c:forEach>
      </c:if>
    </ul>
    <ul class="table-info">
      <!-- 套页需套以下input -->
      <input type="hidden" name="tableNumber" value="${tableId}">
      <input type="hidden" name="peopleNumber" value="${personNum}">
      <input type="hidden" name="customPrice" value="${totalMoney}">
      <li>当前餐桌 : <span class="table-number">${tableId}</span>号桌</li>
      <li>
        <label>餐位人数 ：</label><span class="J_peopleNum">${personNum}</span>人
        <label>餐位费用 ：</label><span class="J_seatPrice">${seatPrice}</span>/人
        <label>餐台费用 ：</label><span class="J_tablePrice">${tablePrice}</span>元
      </li>
      <li><label>已下单消费 ：</label><span class="J_orderedPrice">${totalMoney}</span></li>
    </ul>
  </div>
</div>

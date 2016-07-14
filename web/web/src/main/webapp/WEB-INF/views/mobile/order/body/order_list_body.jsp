<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
  <div id="wrapper" class="scroll">
    <!-- 后端刷菜品备注 -->
     <div class="J_remarkHidden hidden">
        <c:if test="${not empty uniqueRemark}">
          <c:forEach var="remark" items="${uniqueRemark}">
            <span>${remark}</span>
          </c:forEach>
        </c:if>
      </div>
    <div class="order-service">
      <form >
        <label>上菜方式 ：</label>
        <input class='J_restoreValue' type="hidden" value="即起">
        <input class="J_serviceWay" type="radio" value= '0' name="serviceWay" checked/><span>即起</span>
        <input class="J_serviceWay" type="radio" value='1' name="serviceWay"/><span>叫起</span>
        <a class="order-dish J_orderDish" href="#">确认下单</a>
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
          <c:choose>
            <c:when test="${not empty dto.taste}">
                <input type="hidden" name="dishTaste" value="${dto.taste.name}">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="dishTaste" value="菜品口味：默认">
            </c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${not empty dto.remark}">
              <input type="hidden" name="dishRemark" value="${dto.remark}">
            </c:when>
            <c:otherwise>
              <input type="hidden" name="dishRemark" value="无">
            </c:otherwise>
          </c:choose>
          <img class="ordering-dish-img" src="http://static.emenu2.net/uploads${dto.imgPath}" alt="暂无菜品展示图片">
          <div class="ordering-dish-info">
            <p class="info-text">
              <span class="ordering-dish-name">${dto.name}</span>
              <!-- 后端控制原价没有改变时，不显示price span -->
              <c:choose>
                <c:when test="${dto.price eq dto.salePrice}">
                  <span class="ordering-dish-sale">￥${dto.salePrice}</span>
                </c:when>
                <c:otherwise>
                  <span class="ordering-dish-price">￥${dto.price}</span>
                  <span class="ordering-dish-sale">￥${dto.salePrice}</span>
                </c:otherwise>
              </c:choose>
            </p>
            <p class="ordering-number">
              <button class="J_redudeButton"><i class="fa fa-minus"></i></button>
              <input class="ordering-dish-number" type="tel" value="${dto.count}" readonly>
              <button class="J_plusButton"><i class="fa fa-plus"></i></button>
              <span class="ordering-dish-unit">${dto.unitName}</span>
            </p>
            <p class="ordering-remark-info J_remarks">${dto.remark}</p>
          </div>
          <button class="J_delete">删除</button>
        </li>
        </c:forEach>
      </c:if>
    </ul>
    <!--默认刷页时后端返回本单消费的总金额，若客户在订单中调整菜品的数量，前端再其总计金额的基础上再进行计算-->
     <c:if test="${totalMoney >0}">
       <p class="curren-custom font-size-24">本单消费：<span class="general-color J_customPrice">￥${totalMoney}</span></p>
     </c:if>
    <!-- 已下订单菜品列表 -->
    <ul class="ordered-dish-list clearfix J_scroll">
      <c:if test="${not empty orderDishDto}">
         <c:forEach var="dto" items="${orderDishDto}">
            <li class="ordered-dish">
                  <img class="ordered-dish-img" src="http://static.emenu2.net/uploads${dto.imgPath}" alt="暂无菜品展示图片">
                  <div class="ordered-dish-info">
                  <p class="info-text">
                    <span class="ordered-dish-name">${dto.dishName}</span>
                    <span class="ordered-dish-price">￥${dto.price}</span>
                    <span class="ordered-dish-sale">￥${dto.salePrice}</span>
                  </p>
                    <c:choose>
                      <c:when test="${dto.isPackage eq 0}">
                        <p class="ordered-number">已下单，数量 × <span class="J_orderedDishNum">${dto.dishQuantity}</span>
                        <span class =ordered-dish-unit>${dto.unitName}</span></p>
                       </c:when>
                      <c:otherwise>
                        <p class="ordered-number">已下单，数量 × <span class="J_orderedDishNum">${dto.packageQuantity}</span>
                          <span class =ordered-dish-unit>${dto.unitName}</span></p>
                      </c:otherwise>
                     </c:choose>

                  <p class="ordered-remark-info J_remarks">${dto.remark}</p>
                </div>
                  <button class="J_delete">删除</button>
            </li>
         </c:forEach>
        </c:if>
    </ul>
    <ul class="table-info">
      <!-- 套页需套以下name为tableNumberinput和peopleNumber的input -->
      <input type="hidden" name="tableNumber" value="${tableId}">
      <input type="hidden" name="peopleNumber" value="${personNum}">
      <input class="J_returnPrice" type="hidden" name="customPrice" value="">
      <li>当前餐桌 : <span class="table-number">${tableId}</span>号桌</li>
      <li>
        <label>餐位人数 ：</label><span class="J_peopleNum">${personNum}</span>人
        <label>餐位费用 ：</label><span class="J_seatPrice">${seatPrice}</span>/人
        <label>餐台费用 ：</label><span class="J_tablePrice">${tablePrice}</span>元
      </li>
      <li><label>已下单消费 ：</label><span class="J_orderedPrice">￥${orderTotalMoney}</span></li>
    </ul>
  </div>
</div>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
  <div id="wrapper" class="scroll">
    <div class="order-service">
      <form action="" method="">
        <label>上菜方式 ：</label>
        <input class='J_restoreValue' type="hidden" value="即起">
        <input class="J_serviceWay" type="radio" value= '0' name="serviceWay" checked/><span>即起</span>
        <input class="J_serviceWay" type="radio" value='1' name="serviceWay"/><span>叫起</span>
        <a class="order-dish J_orderDish" href="#">点菜</a>
        <!-- <div>
        </div>
        <div></div> -->
        <!-- <div class="call-info">
            <p>
                <label>上菜方式 ：</label>
                <input type="radio" checked="checked" name="serviceWay" value="0" /><span>即起</span>
                <input type="radio" name="serviceWay" value="1" /><span>叫起</span>
            </p>
            <p>
                <label>本次下单消费：</label><span class="curren-custom">￥200</span>
            </p>
        </div>
        <p class="confirm-order">
            <a class="order-dish" href="#">点菜</a>
        </p> -->
      </form>
    </div>
    <!-- 正在下单菜品列表 -->
    <ul class="ordering-list clearfix J_scroll" data-button-type="1">
      <li class="ordering-dish clearfix J_swipeEvent" data-ordering-dish-id="1">
        <!-- 套页需套以下input -->
        <input type="hidden" name="dishId" value="1">
        <input type="hidden" name="dishName" value="黑胡椒牛排">
        <input type="hidden" name="dishPrice" value="28.05">
        <!-- 菜品的最终售价 -->
        <input type="hidden" name="dishUnit" value="份">
        <input type="hidden" name="dishNumber" value="4">
        <input type="hidden" name="dishTaste" value="不麻不辣">
        <input type="hidden" name="dishRemark" value="少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐">
        <img class="ordering-dish-img" src="${staticWebsite}img/mobile/order/order-dish-img.gif" alt="暂无菜品展示图片">
        <div class="ordering-dish-info">
          <p class="info-text">
            <span class="ordering-dish-name">黑胡椒牛排</span>
            <!-- 后端控制原价没有改变时，不显示price span -->
            <span class="ordering-dish-price">￥32</span>
            <span class="ordering-dish-sale">￥28.05</span>
          </p>
          <p class="ordering-number">
            <button class="J_redudeButton"><i class="fa fa-minus"></i></button>
            <input class="ordering-dish-number" type="tel" value="4" readonly></input>
            <button class="J_plusButton"><i class="fa fa-plus"></i></button>
          </p>
          <p class="ordering-remark-info J_remarks">少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐</p>
        </div>
        <button class="J_delete">删除</button>
      </li>
      <li class="ordering-dish clearfix J_swipeEvent" data-ordering-dish-id="2">
        <input type="hidden" name="dishId" value="2">
        <input type="hidden" name="dishName" value="黑胡椒牛排">
        <input type="hidden" name="dishPrice" value="28.05">
        <!-- 菜品的最终售价 -->
        <input type="hidden" name="dishUnit" value="份">
        <input type="hidden" name="dishNumber" value="6">
        <input type="hidden" name="dishTaste" value="多麻辣">
        <input type="hidden" name="dishRemark" value="少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐">
        <img class="ordering-dish-img" src="${staticWebsite}img/mobile/order/order-dish-img.gif" alt="暂无菜品展示图片">
        <div class="ordering-dish-info">
          <p class="info-text">
            <span class="ordering-dish-name">黑胡椒牛排</span>
            <!-- 后端控制原件没有改变时，不显示price span -->
            <span class="ordering-dish-price">￥32</span>
            <span class="ordering-dish-sale">￥28</span>
          </p>
          <p class="ordering-number">
            <button class="J_redudeButton"><i class="fa fa-minus"></i></button>
            <input class="ordering-dish-number" type="tel" value="5" readonly></input>
            <button class="J_plusButton"><i class="fa fa-plus"></i></button>
          </p>
          <p class="ordering-remark-info J_remarks">少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐</p>
        </div>
        <button class="J_delete">删除</button>
      </li>
      <li class="ordering-dish clearfix J_swipeEvent" data-ordering-dish-id="3">
        <input type="hidden" name="dishId" value="3">
        <input type="hidden" name="dishName" value="黑胡椒牛排">
        <input type="hidden" name="dishPrice" value="28.05">
        <!-- 菜品的最终售价 -->
        <input type="hidden" name="dishUnit" value="份">
        <input type="hidden" name="dishNumber" value="6">
        <input type="hidden" name="dishTaste" value="多麻辣">
        <input type="hidden" name="dishRemark" value="少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐">
        <img class="ordering-dish-img" src="${staticWebsite}img/mobile/order/order-dish-img.gif" alt="暂无菜品展示图片">
        <div class="ordering-dish-info">
          <p class="info-text">
            <span class="ordering-dish-name">黑胡椒牛排</span>
            <!-- 后端控制原价没有改变时，不显示price span -->
            <span class="ordering-dish-price">￥32</span>
            <span class="ordering-dish-sale">￥28</span>
          </p>
          <p class="ordering-number">
            <button class="J_redudeButton"><i class="fa fa-minus"></i></button>
            <input class="ordering-dish-number" type="tel" value="6" readonly></input>
            <button class="J_plusButton"><i class="fa fa-plus"></i></button>
          </p>
          <p class="ordering-remark-info J_remarks">少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐,少放盐</p>
        </div>
        <button class="J_delete">删除</button>
      </li>
    </ul>
    <!--默认刷页时后端返回本单消费的总金额，若客户在订单中调整菜品的数量，前端再其总计金额的基础上再进行计算-->
    <p class="curren-custom font-size-24">本单消费：<span class="general-color J_customPrice">￥200.335</span></p>
    <!-- 已下订单菜品列表 -->
    <ul class="ordered-dish-list clearfix J_scroll">
      <li class="ordered-dish">
        <img class="ordered-dish-img" src="${staticWebsite}img/mobile/order/order-dish-img.gif" alt="暂无菜品展示图片">
        <div class="ordered-dish-info">
          <p class="info-text">
            <span class="ordered-dish-name">黑胡椒牛排</span>
            <span class="ordered-dish-price">￥32</span>
            <span class="ordered-dish-sale">￥28</span>
          </p>
          <p class="ordered-number">已下单，数量 × <span class="J_orderedDishNum">5</span></p>
          <p class="ordered-remark-info J_remarks">辣辣辣辣辣辣多辣椒少放盐，多放辣椒,辣辣辣辣辣辣多辣椒少放盐，多放辣椒,辣辣辣辣辣辣多辣椒少放盐，多放辣椒,辣辣辣辣辣辣多辣椒少放盐，多放辣椒</p>
        </div>
        <button class="J_delete">删除</button>
      </li>
    </ul>
    <ul class="table-info">
      <!-- 套页需套以下input -->
      <input type="hidden" name="tableNumber" value="1">
      <input type="hidden" name="peopleNumber" value="4">
      <input type="hidden" name="customPrice" value="200">
      <li>当前餐桌 : <span class="table-number">1</span>号桌</li>
      <li>
        <label>餐位人数 ：</label><span class="J_peopleNum">4</span>人
        <label>餐位费用 ：</label><span class="J_seatPrice">10</span>/人
        <label>餐台费用 ：</label><span class="J_tablePrice">10</span>元
      </li>
      <li><label>已下单消费 ：</label><span class="J_orderedPrice">￥200</span></li>
    </ul>
  </div>
</div>
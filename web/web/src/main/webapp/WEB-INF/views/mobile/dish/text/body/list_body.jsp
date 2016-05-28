<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/28
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
  <div id="wrapper" class="scroll">
    <ul class="dish-list J_scroll">
      <li class="dish-item clearfix" data-dish-id="1">
        <p class="dish-name"><i class="fa fa-circle-o"></i>清蒸鲍鱼</p>
        <p class="dish-price-sale">
          <span class="dish-price">￥32</span>
          <span class="dish-sale">￥28</span>
        </p>
        <p class="add-dish J_addDish" data-dish-number="2">点&nbsp;餐
          <span class="number-tip hidden"></span>
        </p>
      </li>
      <li class="dish-item clearfix" data-dish-id="2">
        <p class="dish-name"><i class="fa fa-circle-o "></i>水煮鱼</p>
        <p class="dish-price-sale">
          <span class="dish-price">￥32</span>
          <span class="dish-sale">￥28</span>
        </p>
        <p class="add-dish J_addDish" data-dish-number="">点&nbsp;餐
          <span class="number-tip hidden"></span>
        </p>
      </li>
    </ul>
  </div>
</div>

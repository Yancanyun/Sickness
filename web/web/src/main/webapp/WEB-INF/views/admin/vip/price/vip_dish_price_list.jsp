<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/1
  Time: 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>会员价列表页</title>
</head>
<body>
<table>
  <tr>
    <th>商品编号</th>
    <th>商品名称</th>
    <th>定价</th>
    <th>售价</th>
    <th>会员价</th>
    <th>差价</th>
    <th>操作</th>
  </tr>
  <tbody>
  <c:forEach items="${vipDishPriceDtoList}" var="vipDishPriceDto">
    <tr>
      <td>${vipDishPriceDto.dishNumber}</td>
      <td>${vipDishPriceDto.dishName}</td>
      <td>${vipDishPriceDto.price}</td>
      <td>${vipDishPriceDto.salePrice}</td>
      <td>${vipDishPriceDto.vipDishPrice}</td>
      <td>${vipDishPriceDto.difference}</td>
      <td>
        <a href="${website}admin/vip/price/update/{vipDishPriceDto.dishId}">编辑</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>

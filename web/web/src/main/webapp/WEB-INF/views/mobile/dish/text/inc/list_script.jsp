<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/28
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="dishTpl">
  {@each list as it}
  <li class="dish-item clearfix" data-dish-id="&{it.dishId}">
    <a href="${website}mobile/dish/detail/&{it.dishId}">
      <p class="dish-name"><i class="fa fa-circle-o"></i>&{it.name}</p>
      <p class="dish-price-sale">
        <span class="dish-price">￥&{it.price}</span>
        <span class="dish-sale">￥&{it.sale}</span>
      </p>
      <p class="add-dish J_addDish" data-dish-number="">点&nbsp;餐
        <span class="number-tip hidden"></span>
      </p>
    </a>
  </li>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/classify-text', function(){
      PW.page.Classify({});
    })
  })
</script>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/template" id="orderMainInfoTpl">
  <p class="choosed-service"><label>上菜方式 ：</label>&{serviceWay}</p>
  <p class="confirm-order-remark"><label>备注 ：</label>
    <textarea class="J_orderRemarkText" type="text" name="confirmOrderRemark" value="" placeholder='请输入备注内容（可不填）' onkeyup="this.value=this.value.substr(0,80)" ></textarea>
  </p>
  <div class="orderMainInfo">
    <p class="confirm-table-number"><label>餐桌 ：</label>&{order.tableNumber}号</p>
    <p class="confirm-people-number"><label>餐位人数 ：</label>&{order.peopleNumber}人</p>
    <p class="confirm-custom-price"><label>本次总计消费 ：</label>￥&{order.customPrice}</p>
  </div>
</script>
<script type="text/template" id="confirmDishTpl">
  <li class="confirm-dish" data-dish-id="&{dialog.dishId}">
    <input type="hidden" name="confirmDishId" value="&{dialog.dishId}">
    <input type="hidden" name="confirmDishNumber" value="&{dialog.dishNumber}">
    <p class="dish-name-num clearfix">
      <label class="dish-name">&{dialog.dishName} : </label><span class="dish-price">￥<em>&{dialog.dishPrice}</em>/&{dialog.dishUnit}</span>
      <span class="dish-number">&{dialog.dishNumber}</span><label class="dish-number-label">数量 : </label>
    </p>
    <p class="dish-taste">&{dialog.dishTaste}</p>
    <p class="dish-remark"><label>备注 ：</label>&{dialog.dishRemark}</p>
  </li>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/order', function(){
      PW.page.orderList({});
    });
  });
</script>
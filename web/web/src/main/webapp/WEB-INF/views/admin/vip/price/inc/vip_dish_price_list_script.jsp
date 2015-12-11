<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/2
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="listTpl">
  {@each list as it}
  <tr data-dish-id="&{it.dishId}">
    <td><input class="J_price" type="checkbox" name="dishIdList" value="&{it.dishId}"/></td>
    <td>&{it.dishNumber}</td>
    <td>&{it.dishName}</td>
    <td>&{it.price}</td>
    <td>&{it.salePrice}</td>
    <td class="J_vipDishPrice">&{it.vipDishPrice}</td>
    <td>
      {@if it.order == 0}
      <i class="fa fa-long-arrow-down"></i>&nbsp;&{it.difference}
      {@else}
      <i class="fa fa-long-arrow-up"></i>&nbsp;&{it.difference}
      {@/if}
    </td>
    <td><a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a></td>
  </tr>
  {@/each}
</script>
<!-- 编辑模板 -->
<script type="text/template" id="editTpl">
  <tr>
    <td class="v-middle"><input class="J_price" type="checkbox"/></td>
    <td class="v-middle">&{dishNumber}</td>
    <td class="v-middle">&{dishName}</td>
    <td class="v-middle">&{price}</td>
    <td class="v-middle">&{salePrice}</td>
    <td class="v-middle"><input type="text" class="form-control J_vipPriceInp" data-valid-rule="notNull" name="vipDishPrice" value="&{vipDishPrice}"/></td>
    <td class="v-middle">
      {@if order == 0}
      <i class="fa fa-long-arrow-down"></i>&nbsp;&{difference}
      {@else}
      <i class="fa fa-long-arrow-up"></i>&nbsp;&{difference}
      {@/if}
    </td>
    <td class="v-middle">
      <a href="javascript:;" class="label-info J_save"><i class="fa fa-save"></i>&nbsp;保存</a>
      <a href="javascript:;" class="label-info J_cancel"><i class="fa fa-undo"></i>&nbsp;取消</a>
    </td>
  </tr>
</script>
<!--弹出框内容模板-->
<script type="text/template" id="dialogTpl">
  <form method="" action="" class="J_addForm">
    <div class="form-group J_type">
      <label>
        <input type="radio" name="type" value="0" checked>按折扣生成
      </label>
      <div class="input-group">
        <input type="text" class="form-control" placeholder="0~10之间的数字" data-valid-rule="scale(0,10,1)" name="discount" value="">
        <span class="input-group-addon">折</span>
      </div>
    </div>
    <div class="form-group J_type">
      <label>
        <input type="radio" name="type" value="1">按固定金额减少生成
      </label>
      <div class="input-group">
        <input type="text" class="form-control" placeholder="降低的金额" data-valid-rule="notNegativeNumber" disabled="disabled" name="difference" value="">
        <span class="input-group-addon">元</span>
      </div>
    </div>
    <div class="form-group">
      <label>生成价格不低于</label>
      <div class="input-group">
        <input type="text" class="form-control" value="1" data-valid-rule="notNegativeNumber" name="lowPrice" value="">
        <span class="input-group-addon">元</span>
      </div>
    </div>
    <div class="checkbox">
      <label>
        <input type="checkbox" value="0" name="includeDrinks">不包括酒水
      </label>
    </div>
    <div class="checkbox">
      <label>
        <input type="checkbox" name="cover" value="1">覆盖已设会员价的项目
      </label>
    </div>
    <input class="J_hidden" type="hidden" name="vipDishPricePlanId" value="${vipDishPricePlan.id}"/>
  </form>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/vip-management/vip-dish-price-management',function(){
      PW.page.VipDishPrice.Core();
    })
  });
</script>

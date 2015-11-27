<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/27
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/template" id="editTpl">
  <tr data-plan-id="" data-oper-type="&{type}">
    <td>
      <input type="text" class="form-control" data-valid-rule="length(0,16)" placeholder="请输入15个字符以内的方案名称" name="name" value="&{plan.name}" />
    </td>
    <td>
      <input type="text" class="form-control" name="description" value="&{plan.description}"/>
    </td>
    <td>
      <a href="javascript:;" class="label-info J_save"><i class="fa fa-save"></i>&nbsp;保存</a>
      <a href="javascript:;" class="label-info J_cancel"><i class="fa fa-undo"></i>&nbsp;取消</a>
    </td>
  </tr>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/vip-management/vip-dish-price-plan-management',function(){
      PW.page.VipDishPricePlan.Core();
    })
  });
</script>
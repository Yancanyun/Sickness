<%@ page contentType="text/html;charset=UTF-8" %>
<!--编辑模板-->
<script type="text/template" id="editTpl">
  <tr recharge-plan-id="&{plan.id}" data-oper-type="&{plan.type}">
    <td>
      <input type="text" class="form-control" data-valid-tip="" data-valid-rule="length(0,16)" placeholder="请输入15个字符以内的方案名称" name="name" value="&{plan.name}" />
    </td>
    <td>
      <input type="text" class="form-control" data-valid-tip="" data-valid-rule="isFloat" placeholder="请输入整数、小数" name="payAmount" value="&{plan.payAmount}"/>
    </td>
    <td>
      <input type="text" class="form-control" data-valid-tip="" data-valid-rule="isFloat" placeholder="请输入整数、小数" name="rechargeAmount" value="&{plan.rechargeAmount}"/>
    </td>
    <td>
      <select class="form-control" name="status">
        {@if plan.status == 0}
        <option value ="0">停用</option>
        <option value ="1">可用</option>
        {@else}
        <option value ="1">可用</option>
        <option value ="0">停用</option>
        {@/if}
      </select>
    </td>
    <td>
      <a href="javascript:;" class="label-info J_save"><i class="fa fa-save"></i>&nbsp;保存</a>
      <a href="javascript:;" class="label-info J_cancel"><i class="fa fa-undo"></i>&nbsp;取消</a>
    </td>
  </tr>
</script>
<!--保存模板-->
<script type="text/template" id="saveTpl">
  <tr recharge-plan-id="&{plan.id}">
    <td class="J_name">&{plan.name}</td>
    <td class="J_payAmount">&{plan.payAmount}</td>
    <td class="J_rechargeAmount">&{plan.rechargeAmount}</td>
    <td class="J_status">&{plan.status}</td>
    <td>
      <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      {@if plan.status == "停用"}
      <a class="label-info J_change" href="javascript:;"><i class="fa fa-circle"></i>&nbsp;启用</a>
      {@else}
      <a class="label-info J_change" href="javascript:;"><i class="fa fa-circle"></i>&nbsp;停用</a>
      {@/if}
      <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/vip-management/vip-recharge-plan-management', function(){
      PW.page.VipRechargePlan.Core();
    })
  });
</script>
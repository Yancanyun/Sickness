<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/template" id="editTpl">
  <tr data-period-id="&{mealPeriod.id}" data-period-oper="&{mealPeriod.oper}">
    <td><input type="text" class="form-control" name="name" value="&{mealPeriod.name}" data-valid-tip="" data-valid-rule="notNull"/></td>
    <td>
      <select class="form-control" name="status">
        {@if mealPeriod.status == 0}
        <option value ="0">停用</option>
        <option value ="1">启用</option>
        {@else}
        <option value ="1">启用</option>
        <option value ="0">停用</option>
        {@/if}
      </select>
    </td>
    <td><input type="text" class="form-control" name="weight" value="&{mealPeriod.weight}" data-valid-tip="" data-valid-rule="isNumber"/></td>
    <td>
      <a href="javascript:;" class="label-info J_save"><i class="fa fa-save"></i>&nbsp;保存</a>
      <a href="javascript:;" class="label-info J_cancel"><i class="fa fa-undo"></i>&nbsp;取消</a>
    </td>
  </tr>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/restaurant-management/meal-period-management', function(S){
      PW.page.MealPeriod.Management();
    });
  });
</script>

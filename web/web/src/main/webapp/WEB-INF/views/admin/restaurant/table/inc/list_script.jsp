<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/template" id="tpl">
  {@each list as it}
  <tr data-table-id="&{it.id}">
    <td><input class="J_table" type="checkbox"/></td>
    <td>&{it.areaName}</td>
    <td>&{it.name}</td>
    <td>&{it.seatNum}</td>
    <td class="J_state">
      {@if it.state == 0}停用
      {@else if it.state == 1}可用
      {@else if it.state == 2}占用已结账
      {@else if it.state == 3}占用未结账
      {@else if it.state == 4}已预订
      {@else if it.state == 5}已并桌
      {@else if it.state == 6}已删除
      {@/if}
    </td>
    <td>&{it.seatFee}元/人</td>
    <td>&{it.tableFee}元</td>
    <td>&{it.minCost}元</td>
    <td>&{it.mealPeriodName}</td>
    <td>
      <a class="label-info J_edit" href="javascript:;" src="${website}admin/restaurant/table/update/&{it.id}"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      {@if it.state == 0}
      <a class="label-info J_change" href="javascript:;"><i class="fa fa-check"></i>&nbsp;启用</a>
      {@else}
      <a class="label-info J_change" href="javascript:;"><i class="fa fa-circle"></i>&nbsp;停用</a>
      {@/if}
      <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/restaurant-management/table-management-list', function(S){
      PW.page.TableManagement.List();
    });
  });
</script>
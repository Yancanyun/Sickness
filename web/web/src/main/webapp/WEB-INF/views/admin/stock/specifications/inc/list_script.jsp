<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 17/3/8
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="tpl">
  {@each list as it}
  <tr data-specification-id="&{it.id}">
    <td class="J_orderUnitId">&{it.orderUnitId}</td>
    <td class="J_orderToStorage">&{it.orderToStorage}</td>
    <td class="J_storageUnitId">&{it.storageUnitId}</td>
    <td class="J_storageToCost">&{it.storageToCost}</td>
    <td class="J_costCardId">&{it.costCardId}</td>
    <td>
      <a class="label-info J_edit" href="#"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/dish-management/specification-management-list', function(S){
      PW.page.DishManagement.SpecificationManagement.List({
        renderTo: '.J_pagination',
        juicerRender: '#tpl',
        dataRender: '#J_template',
        url: '/mock/admin/specification-management-list.json',
        pageSize: 10,
        configUrl: function(url,page,me,prevPaginationData){
          return url;
          // return url + '/' + page;
        }
      });
    });
  });
</script>

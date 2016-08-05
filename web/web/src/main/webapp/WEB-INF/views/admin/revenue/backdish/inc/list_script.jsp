<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="tpl">
  {@each list as it}
  <tr>
    <td>&{it.dishId}</td>
    <td>&{it.day}</td>
    <td>&{it.orderDishTime}</td>
    <td>&{it.backDishTime}</td>
    <td>&{it.intervalTime}</td>
    <td>&{it.backMan}</td>
    <td>&{it.table}</td>
    <td>&{it.dishName}</td>
    <td>&{it.unitPrice}</td>
    <td>&{it.num}</td>
    <td>&{it.allPrice}</td>
    <td>&{it.reason}</td>
  </tr>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/business-analysis/retreat-details', function(){
      PW.page.BusinessAnalysis.RetreatDetails({
        renderTo: '.J_pagination',
        juicerRender: '#tpl',
        dataRender: '#J_template',
        url: '${website}admin/revenue/backdish/ajax/list',
        pageSize: 10,
        configUrl: function(url,page,me,prevPaginationData){
          return url + '/' + page;
        }
      });
    });
  })
</script>
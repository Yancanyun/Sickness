<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="tpl">
  {@each list as it}
  <tr>
    <td>&{it.dishName}</td><!-- 菜品名称-->
    <td>&{it.bigTag}</td><!-- 菜品大类-->
    <td>&{it.num}</td><!-- 销售数量-->
    <td>&{it.consumeSum}</td><!-- 消费金额-->
  </tr>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/business-analysis/dish-sales-ranking', function(){
      PW.page.BusinessAnalysis.DishSalesRanking({
        renderTo: '.J_pagination',
        juicerRender: '#tpl',
        dataRender: '#J_template',
        url: '/mock/admin/dish-sales-ranking.json',
        pageSize: 10,
        configUrl: function(url,page,me,prevPaginationData){
          return ${website}admin/rank/sale/ajax;
          //return url + '/' + page;
        },
        data: '2015-11-11'
      });
    });
  })
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="tpl">
  {@each list as it}
  <tr>
    <td>&{it.bigTag}</td><!-- 菜品大类-->
    <td>&{it.num}</td><!-- 销售数量-->
    <td>&{it.consumeSum}</td><!-- 消费金额-->
  </tr>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/business-analysis/dish-bigtag-sales-ranking', function(){
      PW.page.BusinessAnalysis.DishBigtagSalesRanking({
        renderTo: '.J_pagination',
        juicerRender: '#tpl',
        dataRender: '#J_template',
        url: '${website}admin/rank/bigtag/ajax/list',
        pageSize: 10,
        configUrl: function(url,page,me,prevPaginationData){
          return url+"/"+page;
        },
      });
    });
  })
</script>
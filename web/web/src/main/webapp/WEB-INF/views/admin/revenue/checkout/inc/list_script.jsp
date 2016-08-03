<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/template" id="tpl">
  {@each list as it}
  <tr>
    <td>&{it.checkoutId}</td>
    <td>&{it.tableId}</td>
    <td>&{it.tableName}</td>
    <td>&{it.checkerPartyId}</td>
    <td>&{it.checkoutTime}</td>
    <td>&{it.checkoutType}</td>
    <td>&{it.consumptionMoney}</td>
    <td>&{it.wipeZeroMoney}</td>
    <td>&{it.totalPayMoney}</td>
    <td>&{it.changeMoney}</td>
    <td>&{it.shouldPayMoney}</td>
    <td>
      {@if it.isInvoiced == 0}<i class="fa fa-minus"></i> {@/if}
      {@if it.isInvoiced == 1}<i class="fa fa-check"></i> {@/if}
    </td>
  </tr>
  {@/each}
</script>

<!-- 总计列表模板 -->
<!-- modify -->
<script type="text/template" id="renderSumTpl">
  <tr>
    <td>&{data.billSum}</td>
    <td>&{data.cashSum}</td>
    <td>&{data.vipCardSum}</td>
    <td>&{data.bankCardSum}</td>
    <td>&{data.alipaySum}</td>
    <td>&{data.weChatSum}</td>
    <td>&{data.consumptionMoneySum}</td>
    <td>&{data.wipeZeroMoneySum}</td>
    <td>&{data.totalPayMoneySum}</td>
    <td>&{data.changeMoneySum}</td>
    <td>&{data.shouldPayMoneySum}</td>
    <td>&{data.invoiceSum}</td>
  </tr>
</script>
<!-- modify -->

<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/business-analysis/bill-audit', function(){
      PW.page.BusinessAnalysis.BillAudit({
        renderTo: '.J_pagination',
        juicerRender: '#tpl',
        dataRender: '#J_template',
        url: '${website}admin/revenue/checkout/ajax/list',
        pageSize: 10,
        configUrl: function(url,page,me,prevPaginationData){
          //return url;
          return url + '/' + page;
        }
        //data: '2015-11-11'
      });
    });
  })
</script>
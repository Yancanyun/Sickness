<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/template" id="tpl">
  {@each list as it}
  <tr data-account-id="&{it.id}" data-account-status="&{it.status}">
    <td>&{it.vipGrade}</td>
    <td>&{it.name}</td>
    <td>&{it.phone}</td>
    <td>&{it.cardNumber}</td>
    <td>&{it.balance}</td>
    <td>&{it.integral}</td>
    <td>&{it.totalConsumption}</td>
    <td>&{it.usedCreditAmount}</td>
    {@if it.status == 0}
    <td>停用</td>
    <td>
      <a href="javascript:;" class="label-info J_change"><i class="fa fa-check"></i>&nbsp;启用</a>
    </td>
    {@else}
    <td>启用</td>
    <td>
      <a href="javascript:;" class="label-info J_change"><i class="fa fa-circle"></i>&nbsp;停用</a>
    </td>
    {@/if}
  </tr>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.use('page/vip-management/vip-account-info-management', function (S) {
    PW.page.VipManagement.VipAccountInfoManagement({
      renderTo: '.J_pagination',
      juicerRender: '#tpl',
      dataRender: '#J_template',
      url: '/admin/vip/account/ajax/list',
      pageSize: 10,
      configUrl: function (url, page, me, prevPaginationData) {
      //  return url;
        return url + '/' + page;
      }
    });
  });
</script>

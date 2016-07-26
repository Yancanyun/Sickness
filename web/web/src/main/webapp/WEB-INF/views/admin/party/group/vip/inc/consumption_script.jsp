<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-consumption-id = &{it.consumptionId}>
        <td>&{it.consumptionTime}</td>
        <td>&{it.type}</td>
        <td>&{it.oldMoney}</td>
        <td>&{it.consumptionMoney}</td>
        <td>&{it.realMoney}</td>
        <td>&{it.newMoney}</td>
        <td>&{it.operName}</td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/vip-management/vip-management-consumption-details', function(S){
            PW.page.VipManagement.VipManagementConsumptionDetails({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/admin/party/group/vip/ajax/consumption/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
//                    return url;
                     return url + '/' + page;
                }
            });
        });
    });
</script>

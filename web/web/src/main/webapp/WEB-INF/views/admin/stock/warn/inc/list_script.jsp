<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-ingredient-id="&{it.itemId}">
        <td>&{it.kitchenName}</td>
        <td>&{it.itemId}</td>
        <td>&{it.itemName}</td>
        <td>&{it.warnContent}</td>
        <td>&{it.time}</td>
        <td>
            <a href="${website}admin/stock/documents" class="label-info"><i class="fa fa-pencil"></i>&nbsp;领用</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;忽略</a>
        </td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/store-management/store-forewarning-management-list', function(S){
            PW.page.StoreManagement.StoreForewarningManagementList({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '${website}admin/stock/warn/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
                    //return url;
                    return url + '/' + page;
                }
            });
        });
    });
</script>


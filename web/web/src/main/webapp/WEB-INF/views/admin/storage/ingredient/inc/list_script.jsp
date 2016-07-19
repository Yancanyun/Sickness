<%@ page contentType="text/html;charset=UTF-8" %>

<!--分页模板-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-ingredient-id="&{it.id}">
        <td>&{it.sequenceNumber}</td>
        <td>&{it.name}</td>
        <td>&{it.ingredientNumber}</td>
        <td>&{it.assistantCode}</td>
        <td>&{it.tagName}</td>
        <td>&{it.maxStorageQuantityStr}</td>
        <td>&{it.minStorageQuantityStr}</td>
        <td>&{it.averagePrice}</td>
        <td>&{it.realQuantityStr}</td>
        <td>&{it.realMoney}</td>
        <td>&{it.totalQuantityStr}</td>
        <td>&{it.totalMoney}</td>
        <td>
            <a href="${website}admin/storage/ingredient/toupdate/&{it.id}" class="label-info"><i class="fa fa-pencil">&nbsp;编辑</i></a>
            <a href="JavaScript:;" class="label-info J_del"><i class="fa fa-times">&nbsp;删除</i></a>
            <a href="${website}admin/storage/ingredient/todetails/&{it.id}" class="label-info"><i class="fa fa-search"></i>&nbsp;查看详情</a>
        </td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/store-management/ingredients-management-list', function(S){
            PW.page.StoreManagement.IngredientsManagementList({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '${website}admin/storage/ingredient/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
//                    return url;
                     return url + '/' + page;
                }
            });
        });
    })
</script>
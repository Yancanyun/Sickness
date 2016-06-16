<%@ page contentType="text/html;charset=UTF-8" %>

<!--分页模板-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-store-item-id="&{it.id}">
        <td>&{it.id}</td>
        <td>&{it.name}</td>
        <td>&{it.itemNumber}</td>
        <td>&{it.assistantCode}</td>
        <td>&{it.ingredientName}</td>
        <td>&{it.tagName}</td>
        <td>&{it.supplierName}</td>
        <td>&{it.totalStockInQuantityStr}</td>
        <td>&{it.totalStockInMoney}</td>
        <td>&{it.lastStockInPrice}</td>
        <td>&{it.maxStorageQuantityStr}</td>
        <td>&{it.minStorageQuantityStr}</td>
        <td>&{it.stockOutType}</td>
        <td>
            <a href="${website}admin/storage/item/update/&{it.id}" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="JavaScript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            <a href="${website}admin/storage/item/todetails/&{it.id}" class="label-info"><i class="fa fa-search"></i>&nbsp;查看详情</a>
        </td>
    </tr>
    {@/each}
</script>

<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/store-management/store-item-management', function(S){
            PW.page.StoreManagement.StoreItemManagement.List({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '${website}admin/storage/item/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
//                    return url;
                    return url + '/' + page;
                }
            });
        });
    });
</script>
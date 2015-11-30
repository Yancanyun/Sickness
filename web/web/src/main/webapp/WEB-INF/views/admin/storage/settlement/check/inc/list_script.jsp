<%@ page contentType="text/html;charset=UTF-8" %>

<!--刷分页模板-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr>
        <td>&{it.tagName}</td>
        <td>&{it.itemNumber}</td>
        <td>&{it.itemName}</td>
        <td>&{it.lastStockInPrice}</td>
        <td>&{it.orderUnitName}</td>
        <td>&{it.storageUnitName}</td>
        <td>&{it.beginQuantity}</td>
        <td>&{it.beginMoney}</td>
        <td>&{it.stockInQuantity}</td>
        <td>&{it.stockInMoney}</td>
        <td>&{it.stockOutQuantity}</td>
        <td>&{it.stockOutMoney}</td>
        <td>&{it.IncomeLossQuantity}</td>
        <td>&{it.IncomeLossMoney}</td>
        <td>&{it.totalQuantity}</td>
        <td>&{it.totalAveragePrice}</td>
        <td>&{it.totalMoney}</td>
        <td>&{it.maxStorageQuantity}</td>
        <td>&{it.minStorageQuantity}</td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/store-management/inventory-management',function(){
            PW.page.StoreManagement.InventoryManagement({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/admin/storage/settlement/check/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
                    // return url;
                    return url + '/' + page;
                }
            });
        });
    })
</script>
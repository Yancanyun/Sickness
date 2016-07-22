<%@ page contentType="text/html;charset=UTF-8" %>

<!--刷分页模板-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr>
        <td>&{it.sequenceNumber}</td><!-- 序号 -->
        <td>&{it.tagName}</td><!-- 类别 -->
        <td>&{it.ingredientName}</td><!-- 名称 -->
        <td>&{it.ingredientNumber}</td><!-- 原配料编号 -->
        <td>&{it.orderUnitName}</td><!-- 订货规格 -->
        <td>&{it.storageUnitName}</td><!-- 库存 -->
        <td>&{it.costCardUnitName}</td><!-- 成本卡 -->
        <td>&{it.beginQuantityStr}</td><!-- 期初数量 -->
        <td>&{it.stockInQuantityStr}</td><!-- 入库数量 -->
        <td>&{it.stockOutQuantityStr}</td><!-- 出库 -->
        <td>&{it.incomeLossQuantityStr}</td><!-- 盈亏 -->
        <td>&{it.totalQuantityStr}</td><!-- 结存 -->
        <td>&{it.maxStorageQuantityStr}</td><!-- 警报值上限 -->
        <td>&{it.minStorageQuantityStr}</td><!-- 警报值下限 -->
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
                url: 'check/ajax/list/',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
//                    return url;
                     return url + '/' + page;
                }
            });
        });
    })
</script>
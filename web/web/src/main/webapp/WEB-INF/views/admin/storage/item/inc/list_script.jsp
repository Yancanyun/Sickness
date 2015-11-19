<%@ page contentType="text/html;charset=UTF-8" %>

<!--分页模板-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-store-item-id="&{it.id}">
        <td>&{it.itemNumber}</td>
        <td>&{it.assistantCode}</td>
        <td>&{it.name}</td>
        <td>&{it.tagName}</td>
        <td>&{it.supplierName}</td>
        <td>&{it.maxStorageQuantity}</td>
        <td>&{it.minStorageQuantity}</td>
        <td>&{it.stockOutType}</td>
        <td>
            <a href="${website}admin/storage/item/update/&{it.id}" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
        </td>
    </tr>
    {@/each}
</script>

<script type="text/javascript">
    KISSY.ready(function (S) {
        S.use('page/store-management/store-item-management', function (S) {
            PW.page.StoreManagement.StoreItemManagement.List({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '${website}admin/storage/item/ajax/list',
                pageSize: 10,
                configUrl: function (url, page, me, prevPaginationData) {
//                    return url;
                    return url + '/' + page;
                }
            });
        });
    });
</script>
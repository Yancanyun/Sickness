<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/template" id="renderTpl">
    {@each list.items as it}
    <tr>
        {@if it.status == 1}
        <td rowspan=&{length}>&{list.supplierName}</td>
        {@/if}

        <td>&{it.itemName}</td>
        <td>&{it.itemQuantity}</td>
        <td>&{it.itemMoney}</td>
        <td>&{it.handlerName}</td>
        <td>&{it.createdName}</td>

        {@if it.status == 1}
        <td rowspan=&{length}>&{list.totalMoney}</td>
        {@/if}
    </tr>
    {@/each}
</script>

<script type="text/javascript">
    KISSY.ready(function(S){
        S.use("page/store-management/settlement-management", function(S){
            PW.page.StoreManagement.SettlementManagement();
        });
    });
</script>
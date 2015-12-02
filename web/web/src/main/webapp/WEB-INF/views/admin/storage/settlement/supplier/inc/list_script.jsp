<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/template" id="renderTpl">
    {@each list as it}
    {@if it.items == ''}
    <tr>
        <td rowspan=''>&{it.supplierName}</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td rowspan=''>&{it.totalMoney}</td>
    </tr>
    {@else}
    {@each it.items as item}
    {@if item.status == 1}
    <tr class="J_supplier">
        <td rowspan=&{item.length}>&{it.supplierName}</td>
        {@else}
    <tr>
        {@/if}
        <td>&{item.itemName}</td>
        <td>&{item.itemQuantity}</td>
        <td>&{item.itemMoney}</td>
        <td>&{item.handlerName}</td>
        <td>&{item.createdName}</td>
        {@if item.status == 1}
        <td rowspan=&{item.length}>&{it.totalMoney}</td>
        {@/if}
    </tr>
    {@/each}
    {@/if}
    {@/each}
</script>

<script type="text/javascript">
    KISSY.ready(function(S){
        S.use("page/store-management/settlement-management", function(S){
            PW.page.StoreManagement.SettlementManagement();
        });
    });
</script>
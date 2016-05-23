<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/template" id="renderTpl">
    {@each list as it}
    <tr>
        <td>&{it.category}</td>
        <td>&{it.tag}</td>
        <td>&{it.dishNumber}</td>
        <td>&{it.name}</td>
        <td>&{it.unit}</td>
        <td>&{it.price}</td>
        <td>&{it.discount}</td>
        <td>&{it.salePrice}</td>

        {@if it.status == 0 }
        <td>停售</td>
        {@/if}

        {@if it.status == 1 }
        <td>销售中</td>
        {@/if}

        {@if it.status == 2 }
        <td>标缺</td>
        {@/if}

        {@if it.status == 3 }
        <td>已删除</td>
        {@/if}

        <%--<td>&{it.lastMonthSales}</td>--%>
        <%--<td>&{it.totalSales}</td>--%>

        {@if it.status == 0 || it.status == 2 || it.status == 3 }
        <td></td>
        {@/if}

        {@if it.status == 1 }
        <td>
            <input type="checkbox" name="dishId" value=&{it.id}>
        </td>
        {@/if}
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/dish-management/sales-ranking-add',function(){
            PW.page.DishManagement.SalesRankingAdd();
        });
    });
</script>
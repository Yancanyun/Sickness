<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-dish-id="&{it.id}" data-dish-status="&{it.status}">
        <td>&{it.dishNumber}</td>
        <td>&{it.assistantCode}</td>
        <td>&{it.name}</td>
        <td>&{it.unitName}</td>
        <td>&{it.price}</td>
        <td>&{it.salePrice}</td>
        <td>
            {@if it.status == 0}停售{@/if}
            {@if it.status == 1}销售中{@/if}
            {@if it.status == 2}标缺{@/if}
        </td>
        <td>&{it.likeNums}</td>
        <td>
            <a href="${website}admin/dish/update/&{it.id}" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            {@if it.status == 0}
            <a href="javascript:;" class="label-info J_status"><i class="fa fa-check"></i>&nbsp;恢复</a>
            {@/if}
            {@if it.status == 1}
            <a href="javascript:;" class="label-info J_status"><i class="fa fa-circle"></i>&nbsp;停售</a>
            {@/if}
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
        </td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function (S) {
        S.use('page/dish-management/dish-management-list', function (S) {
            PW.page.DishManagement.List({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '${website}admin/dish/ajax/list',
                pageSize: 10,
                configUrl: function (url, page, me, prevPaginationData) {
//                    return url;
                    return url + '/' + page;
                }
            });
        });
    });
</script>
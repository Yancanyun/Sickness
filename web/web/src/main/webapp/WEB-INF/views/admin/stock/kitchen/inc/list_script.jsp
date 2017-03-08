<%@ page contentType="text/html;charset=utf-8"%>
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-depot-id="&{it.id}">
        <td>&{it.depotName}</td>
        <td>&{it.responsiblePerson}</td>
        <td>&{it.introduction}</td>
        <td>
            <a href="${website}admin/stock/kitchen/toedit" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            <a href="#" class="label-info"><i class="fa fa-search"></i>&nbsp;查看物品</a>
        </td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use("page/store-management/depot-management-list", function(S){
            PW.page.StoreManagement.DepotManagementList({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/mock/admin/depot-management-list.json',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
                    return url;
                    // return url + '/' + page;
                }
            });
        });
    });
</script>
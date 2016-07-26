<%--
  User: chenyuting
  Time: 2015/11/3 09:54
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-vip-id="&{it.id}" data-status="&{it.status}">
        <td>&{it.name}</td>
        <td>&{it.phone}</td>
        <td>&{it.cardNum}</td>
        <td>&{it.money}</td>
        <td>
            {@if it.status == 2}停用{@/if}
            {@if it.status == 1}启用{@/if}
        </td>
        <td>
            {@if it.status == 2}
            <a href="javascript:;" class="label-info J_change"><i class="fa fa-check"></i>&nbsp;启用</a>
            {@/if}
            {@if it.status == 1}
            <a href="javascript:;" class="label-info J_change"><i class="fa fa-circle"></i>&nbsp;停用</a>
            {@/if}
            <a href="${website}admin/party/group/vip/update/&{it.id}" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            <a href="${website}admin/party/group/vip/detail/&{it.id}" class="label-info"><i class="fa fa-info-circle"></i>&nbsp;查看详情</a>
            <a href="${website}admin/party/group/vip/consumption/&{it.partyId}" class="label-info"><i class="fa fa-list-ul"></i>&nbsp;消费详情</a>
        </td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/vip-management/vip-management-list', function(S){
            PW.page.VipManagement.VipManagementList({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/admin/party/group/vip/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
                    //return url;
                    return url + '/' + page;
                }
            });
        });
    });
</script>
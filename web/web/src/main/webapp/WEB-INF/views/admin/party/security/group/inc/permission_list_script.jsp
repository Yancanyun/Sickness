<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--刷分页-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-authority-id="&{it.id}">
        <td class="J_exp">&{it.permissionExpression}</td>
        <td class="J_desc">&{it.permissionDescription}</td>
        <td>
            <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i> 删除</a>
        </td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function (S) {
        S.use('page/authority-management/authority-group-config', function (S) {
            PW.page.AuthoritGroupConfig({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '${website}admin/party/security/group/permission/${securityGroup.id}/ajax',
                pageSize: 10,
                configUrl: function (url, page, me, prevPaginationData) {
                    // return url;
                    return url + '/' + page;
                }
            });
        });
    });
</script>
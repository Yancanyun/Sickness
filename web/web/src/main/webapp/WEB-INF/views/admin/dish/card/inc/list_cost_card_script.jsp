<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<script type="text/template" id="tpl">
    {@each list as item}
    <tr data-costCard-id="&{item.id}">
        <td>&{item.costCardNumber}</td>
        <td>&{item.name}</td>
        <td>&{item.assistantCode}</td>
        <td>&{item.mainCost}</td>
        <td>&{item.assistCost}</td>
        <td>&{item.deliciousCost}</td>
        <td>&{item.standardCost}</td>
        <td>
            <a href="#" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            <a href="#;" class="label-info"><i class="fa fa-search"></i>&nbsp;查看</a>
        </td>
    </tr>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/dish-management/cost-card-management',function(){
            PW.page.DishManagement.CostCardManagement({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/admin/dish/cost/card/ajax/list/cost/card',
                pageSize: 10,//测试使用，原来为10
                configUrl: function(url,page,me,prevPaginationData){
                    //return url;
                    return url + '/' + page;
                }
            });
        })
    })
</script>

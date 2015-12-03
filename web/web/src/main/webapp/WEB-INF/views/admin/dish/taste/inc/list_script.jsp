<%@ page contentType="text/html;charset=UTF-8" %>

<!--刷分页-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-taste-id="&{it.id}">
        <td class="J_name">&{it.name}</td>
        <td class="J_relatedCharge">&{it.relatedCharge}</td>
        <td>
            <a class="label-info J_editBtn" href="javascript:;"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a class="label-info J_delBtn" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
        </td>
    </tr>
    {@/each}
</script>

<!-- 编辑模板 -->
<script type="text/template" id="editTpl">
    <tr data-taste-id="&{taste.id}" oper-type="&{taste.operType}">
        <td><input class="form-control" type="text" data-valid-tip="请输入口味名称|口味名称输入有误，请重新输入" data-valid-rule="notNull" name="name" value="&{taste.name}"/>
        </td>
        <td>
            <input class="form-control J_relatedChargeInp" type="text" data-valid-tip="请输入关联收费内容|关联收费内容不能为非数字，请重新输入" data-valid-rule="isFloat" value="&{taste.relatedCharge}" name="relatedCharge" />
        </td>
        <td>
            <a href="javascript:;" class="label-info J_saveBtn"><i class="fa fa-save"></i>&nbsp;保存</a>
            <a href="javascript:;" class="label-info J_cancelBtn"><i class="fa fa-undo"></i>&nbsp;取消</a>
        </td>
    </tr>
</script>

<!-- 保存模板 -->
<script type="text/template" id="saveTpl">
    <tr data-taste-id="&{taste.id}">
        <td class="J_name">&{taste.name}</td>
        <td class="J_relatedCharge">&{taste.relatedCharge}</td>
        <td>
            <a class="label-info J_editBtn" href="javascript:;"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a class="label-info J_delBtn" href="javasc ript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
        </td>
    </tr>
</script>

<script type= "text/javascript">
    KISSY.ready(function(S){
        S.use('page/dish-management/dish-taste-management', function(){
            PW.page.DishManagement.List({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/admin/dish/taste/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
                    //return url;
                    return url + '/' + page;
                }
            });
        });
    });
</script>
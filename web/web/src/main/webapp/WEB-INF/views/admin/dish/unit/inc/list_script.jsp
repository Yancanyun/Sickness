<%@ page contentType="text/html;charset=UTF-8" %>

<!--刷分页-->
<script type="text/template" id="tpl">
  {@each list as it}
  <tr data-ingredient-id="&{it.id}">
    <td class="J_exp">&{it.name}</td>
    <td>
      <a class="label-info J_edit" href="javascript:;"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
  {@/each}
</script>
<!-- 添加模板 -->
<script type="text/template" id="editTpl">
  <tr data-ingredient-id="&{ingredient.id}" oper-type="&{ingredient.type}">
    <td><input type="text" class="w200" data-valid-tip="请输入关键字|关键字输入有误" data-valid-rule="notNull" name="name" value="&{ingredient.exp}"/>
    </td>
    <td>
      <a href="javascript:;" class="label-info J_save">
        <i class="fa fa-save"></i>&nbsp;保存
      </a>&nbsp;
      <a href="javascript:;" class="label-info J_cancel">
        <i class="fa fa-undo"></i>&nbsp;取消
      </a>&nbsp;
    </td>
  </tr>
</script>
<!-- 编辑模板 -->
<script type="text/template" id="saveTpl">
  <tr data-ingredient-id="&{ingredient.id}">
    <td class="J_exp">&{ingredient.exp}</td>
    <td>
      <a class="label-info J_edit" href="javascript:;"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/ingredient-management/ingredient-unit-management', function(S){
      PW.page.IngredientUnitManagement({
        renderTo: '.J_pagination',
        juicerRender: '#tpl',
        dataRender: '#J_template',
        url: '/admin/dish/unit/ajax/list',
        pageSize: 10,
        configUrl: function(url,page,me,prevPaginationData){
          //return url;
          return url + '/' + page;
        }
      });
    });
  });
</script>

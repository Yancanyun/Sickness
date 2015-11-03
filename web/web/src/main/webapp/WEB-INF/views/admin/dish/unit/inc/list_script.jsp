<%@ page contentType="text/html;charset=UTF-8" %>

<!--刷分页-->
<script type="text/template" id="tpl">
  {@each list as it}
  <tr data-unit-id="&{it.id}">
    <td class="J_sort">&{it.type}</td>
    <td class="J_name">&{it.name}</td>
    <td>
      <a class="label-info J_edit" href="javascript:;"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
  {@/each}
</script>
<!-- 添加模板 -->
<script type="text/template" id="editTpl">
  <tr data-unit-id="&{unit.id}" oper-type="&{unit.operType}">
    <td>
      <select class="form-control" name="type">
        <option value="1">重量单位</option>
        <option value="2">数量单位</option>
      </select>
    </td>
    <td><input type="text" data-valid-tip="请输入单位|单位输入有误，请重新输入" data-valid-rule="notNull" name="name" value="&{unit.name}"/>
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
  <tr data-unit-id="&{unit.id}">
    <td class="J_sort">
      {@if unit.type == 1}重量单位{@/if}
      {@if unit.type == 2}数量单位{@/if}
    </td>
    <td class="J_name">&{unit.name}</td>
    <td>
      <a class="label-info J_edit" href="javascript:;"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/dish-management/unit-management', function(S){
      PW.page.UnitManagement({
        renderTo: '.J_pagination',
        juicerRender: '#tpl',
        dataRender: '#J_template',
        url: '/admin/dish/unit/ajax/list',
        pageSize: 10,
        configUrl: function(url,page,me,prevPaginationData){
          return url + '/' + page;
        }
      });
    });
  });
</script>

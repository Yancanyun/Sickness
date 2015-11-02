<%@ page contentType="text/html;charset=UTF-8" %>
<!--编辑模板-->
<script type="text/template" id="editTpl">
  <tr table-area-id="&{area.id}" oper-type="&{area.type}">
    <td class="J_desc col-sm-8"><input type="text" class="form-control" data-valid-tip="" data-valid-rule="notNull" name="name" value="&{area.desc}"/></td>
    <td class="col-sm-4">
      <a href="javascript:;" class="label-info J_saveBtn">
        <i class="fa fa-save"></i>&nbsp;保存</a>&nbsp;
      <a href="javascript:;" class="label-info J_cancelBtn">
        <i class="fa fa-undo"></i>&nbsp;取消</a>&nbsp;
    </td>
  </tr>
</script>
<!--保存模板-->
<script type="text/template" id="saveTpl">
  <tr table-area-id="&{area.id}">
    <td  class="J_desc col-sm-8">&{area.desc}</td>
    <td class="col-sm-4">
      <a class="label-info J_editBtn" href="javascript:;">
        <i class="fa fa-pencil"></i>&nbsp;编辑</a>&nbsp;
      <a class="label-info J_delBtn" href="javascript:;">
        <i class="fa fa-times"></i>&nbsp;删除</a>&nbsp;
    </td>
  </tr>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/restaurant-management/table-area-management', function(S){
      PW.page.AreaManagement({
      });
    });
  });
</script>
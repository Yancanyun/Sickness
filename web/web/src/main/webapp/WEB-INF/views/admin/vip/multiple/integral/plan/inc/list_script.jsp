<%@ page contentType="text/html;charset=UTF-8" %>
<!--编辑模板-->
<script type="text/template" id="beforeSaveTpl">
  <tr data-plan-id="&{data.id}" data-status="&{data.status}">
    <input type="hidden" name="id" value="&{data.id}" />
    <td>
      <input type="text" class="form-control" data-valid-rule="length(0,16)" placeholder="请输入1~15个字符" name="name" value="&{data.name}" />
    </td>
    <td>
      <input type="text" class="form-control" data-valid-rule="isFloat" placeholder="请输入一个小数" name="integralMultiples" value="&{data.integralMultiples}" />
    </td>
    <td>
      <input type="text" class="w190 date" readonly name="startTime" value="&{data.startTime}" />
    </td>
    <td>
      <input type="text" class="w190 date" readonly name="endTime" value="&{data.endTime}" />
    </td>
    <td>
      <select class="form-control" data-valid-rule="scale(0,1,1)" name="status">
        {@if data.status == 0 }
        <option value="0" selected>停用</option>
        <option value="1">启用</option>
        {@/if}
        {@if  data.status == 1 }
        <option value="0">停用</option>
        <option value="1" selected>启用</option>
        {@/if}
        {@if  data.status != 0 && data.status != 1 }
        <option value="0">停用</option>
        <option value="1">启用</option>
        {@/if}
      </select>
    </td>
    <td>
      <a href="javascript:;" class="label-info J_save"><i class="fa fa-save"></i>&nbsp;保存</a>
      <a href="javascript:;" class="label-info J_cancel"><i class="fa fa-undo"></i>&nbsp;取消</a>
    </td>
  </tr>
</script>
<script type="text/template" id="afterSaveTpl">
  <tr data-plan-id="&{data.id}" data-status="&{data.status}">
    <td>&{data.name}</td>
    <td>&{data.integralMultiples}</td>
    <td>&{data.startTime}</td>
    <td>&{data.endTime}</td>
    {@if data.status == 0}
    <td>停用</td>
    <td>
      <a href="javascript:;" class="label-info J_change"><i class="fa fa-check"></i>&nbsp;启用</a>
      <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
    {@else}
    <td>启用</td>
    <td>
      <a href="javascript:;" class="label-info J_change"><i class="fa fa-circle"></i>&nbsp;停用</a>
      <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
    {@/if}
  </tr>
</script>
<script type="text/javascript">
  KISSY.use('page/vip-management/multi-integration-plan-management', function(S){
    PW.page.VipManagement.MultiIntegrationPlanManagement();
  });
</script>
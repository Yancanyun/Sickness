<%@ page contentType="text/html;charset=UTF-8" %>

<!--刷分页-->
<script type="text/template" id="tpl">
  {@each list as it}
  <tr data-authority-id="&{it.id}">
    <td class="J_exp">&{it.name}</td>
    <td class="J_desc">&{it.description}</td>
    <td>
      <a class="label-info J_edit" href="javascript:;"><i class="fa fa-pencil"></i> 编辑</a>
      <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i> 删除</a>
      <a class="label-info J_search" href="#"><i class="fa fa-search"></i> 查看权限</a>
    </td>
  </tr>
  {@/each}
</script>
<!-- 编辑模板 -->
<script type="text/template" id="editTpl">
  <tr data-authority-id="&{authority.id}" oper-type="&{authority.type}">
    <td><input type="text" class="form-control" name="exp" value="&{authority.exp}"/></td>
    <td><input type="text" class="form-control" name="desc" value="&{authority.desc}"/></td>
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
<!-- 保存模板 -->
<script type="text/template" id="saveTpl">
  <tr data-authority-id="&{authority.id}">
    <td class="J_exp">&{authority.exp}</td>
    <td class="J_desc">&{authority.desc}</td>
    <td>
      <a class="label-info J_edit" href="javascript:;"><i class="fa fa-pencil"></i> 编辑</a>
      <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i> 删除</a>
      <a class="label-info J_search" href="#"><i class="fa fa-search"></i> 查看权限</a>
    </td>
  </tr>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/authority-management/authority-config', function(S){
      PW.page.AuthorityConfig({
        renderTo: '.J_pagination',
        juicerRender: '#tpl',
        dataRender: '#J_template',
        url: '/admin/party/security/group/ajax/list',
        pageSize: 10,
        configUrl: function(url,page,me,prevPaginationData){
          //return url;
           return url + '/' + page;
        }
      });
    });
  });
</script>

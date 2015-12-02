<%@ page contentType="text/html;charset=UTF-8" %>
<script type= "text/template" id= "listTpl">
  {@each list as it}
  <div class="col-sm-12 remark-classify">
    <p class="col-sm-9 no-padding-left J_classifyName" data-big-tag-id="&{it.bigTagId}"  data-big-tag-name="&{it.bigTagName}" data-small-tag-id="&{it.smallTagId}" data-small-tag-name="&{it.smallTagName}" >备注分类名称 :&nbsp;&{it.smallTagName}
      <a href="javascript:;" class="J_foldToggle"></a>
    </p>
    <div class="J_remarkHiddenInp">
      <input type="hidden" name="pId" value="&{it.bigTagId}">
      <input type="hidden" name="id" value="&{it.smallTagId}">
      <input type="hidden" name="name" value="&{it.smallTagName}">
    </div>
    <a href="javascript:;" class="label-info oper pull-right J_delSmallRemarkTag"><i class="fa fa-times"></i>&nbsp;删除</a>
    <a href="javascript:;" class="label-info oper pull-right J_editSmallRemarkTag"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
    <a href="javascript:;" class="label-info oper pull-right J_addRemarkContent"><i class="fa fa-plus"></i>&nbsp;添加备注内容</a>
  </div>
  <table class="table table-hover table-bordered J_contentTable" data-small-tag-id="&{it.smallTagId}" data-small-tag-name="&{it.smallTagName}" style="display:none;">
    <thead>
    <th>备注内容</th>
    <th>权重</th>
    <th>关联收费</th>
    <th>是否常用</th>
    <th>操作</th>
    </thead>
    <tbody>
    {@each it.contentList as item}
    <tr data-remark-content-id="&{item.contentId}" data-remark-content="&{item.content}">
      <input type="hidden" name="smallTagId" value="&{it.smallTagId}">
      <input type="hidden" name="id" value="&{item.contentId}">
      <input type="hidden" name="name" value="&{item.content}">
      <input type="hidden" name="weight" value="&{item.weight}">
      <input type="hidden" name="relatedCharge" value="&{item.relatedCharge}">
      <input type="hidden" name="isCommon" value="&{item.isCommon}">
      <td class="col-sm-5 J_content">&{item.content}</td>
      <td class="col-sm-1 J_weight">&{item.weight}</td>
      <td class="col-sm-3 J_relatedCharge">&{item.relatedCharge}</td>
      {@if item.isCommon == 0}
      <td class="col-sm-1 J_isCommon">否</td>
      {@else}
      <td class="col-sm-1 J_isCommon">是</td>
      {@/if}
      <td class="col-sm-2">
        <a href="javascript:;" class="label-info oper J_editRemarkContent"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
        <a href="javascript:;" class="label-info oper J_delRemarkContent"><i class="fa fa-times"></i>&nbsp;删除</a>
      </td>
    </tr>
    {@/each}
    </tbody>
  </table>
  {@/each}
</script>
<!-- 打开备注分类对话框模板 -->
<script type= "text/template" id= "smallTagDlgTpl">
  <form class="form-horizontal J_addForm" action="" method="">
    <input type="hidden" name="pId" value=""/>
    <input type="hidden" name="id" value=""/>
    <div class="form-group">
      <label class="col-sm-3 control-label"><span class="requires">*</span>上级分类</label>
      <div class="col-sm-4">
        <p class="form-control-static J_bigClassify">&{bigTagName}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label"><span class="requires">*</span>分类名称</label>
      <div class="col-sm-9">
        <input class="J_smallClassifyInp" type="text" data-valid-tip="请输入备注分类名称|备注分类名称不能为空，请重新输入" data-valid-rule="notNull" value="&{smallTagName}" name="name" />
      </div>
    </div>
  </form>
</script>
<!-- 添加备注分类 -->
<script type= "text/template" id= "smallTagTpl">
  <div class="col-sm-12 remark-classify">
    <p class="col-sm-9 no-padding-left J_classifyName" data-big-tag-id="&{pId}"  data-big-tag-name="&{bigTagName}" data-small-tag-id="&{id}" data-small-tag-name="&{name}" >备注分类名称 :&nbsp;&{name}
      <a href="javascript:;" class="J_foldToggle"></a>
    </p>
    <div class="J_remarkHiddenInp">
      <input type="hidden" name="pId" value="&{pId}">
      <input type="hidden" name="id" value="&{id}">
      <input type="hidden" name="name" value="&{name}">
    </div>
    <a href="javascript:;" class="label-info oper pull-right J_delSmallRemarkTag"><i class="fa fa-times"></i>&nbsp;删除</a>
    <a href="javascript:;" class="label-info oper pull-right J_editSmallRemarkTag"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
    <a href="javascript:;" class="label-info oper pull-right J_addRemarkContent"><i class="fa fa-plus"></i>&nbsp;添加备注内容</a>
  </div>
  <table class="table table-hover table-bordered J_contentTable" data-small-tag-id="&{id}" data-small-tag-name="&{name}" style="display:none;">
    <tbody></tbody>
  </table>
</script>
<!-- 表格表头 -->
<script type="text/template" id= "tableHeadTpl">
  <thead>
  <th>备注内容</th>
  <th>权重</th>
  <th>关联收费</th>
  <th>是否常用</th>
  <th>操作</th>
  </thead>
</script>
<!-- 编辑备注分类 -->
<script type= "text/template" id= "smallEditTagTpl">
  <div class="col-sm-12 remark-classify">
    <p class="col-sm-9 no-padding-left J_classifyName" data-big-tag-id="&{pId}"  data-big-tag-name="&{bigTagName}" data-small-tag-id="&{id}" data-small-tag-name="&{name}" >备注分类名称 :&nbsp;&{name}
      <a href="javascript:;" class="J_foldToggle">展开 <<</a>
    </p>
    <div class="J_remarkHiddenInp">
      <input type="hidden" name="pId" value="&{pId}">
      <input type="hidden" name="id" value="&{id}">
      <input type="hidden" name="name" value="&{name}">
    </div>
    <a href="javascript:;" class="label-info oper pull-right J_delSmallRemarkTag"><i class="fa fa-times"></i>&nbsp;删除</a>
    <a href="javascript:;" class="label-info oper pull-right J_editSmallRemarkTag"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
    <a href="javascript:;" class="label-info oper pull-right J_addRemarkContent"><i class="fa fa-plus"></i>&nbsp;添加备注内容</a>
  </div>
</script>
<!-- 备注内容对话框模板 -->
<script type="text/template" id= "contentDlgTpl">
  <form class="form-horizontal J_addContentForm" action="" method="">
    <input type="hidden" name="smallTagId" value="">
    <input type="hidden" name="id" value="">
    <div class="form-group">
      <label class="col-sm-3 control-label"><span class="requires">*</span>备注分类</label>
      <div class="col-sm-4">
        <p class="form-control-static J_smallTag">&{dlg.smallTagName}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label"><span class="requires">*</span>备注内容</label>
      <div class="col-sm-9">
        <input class="J_weightInp" type="text" data-valid-tip="请输入备注内容|备注内容不能为空，请重新输入" data-valid-rule="notNull" value="&{dlg.name}" name="name" />
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label"><span class="requires">*</span>权重</label>
      <div class="col-sm-9">
        <input class="J_weightInp" type="text" data-valid-tip="请输入权重|权重不能为非数字，请重新输入" data-valid-rule="isNumber" value="&{dlg.weight}" name="weight" />
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label"><span class="requires">*</span>是否常用</label>
      <div class="col-sm-9">
        <div class="radio">
          {@if dlg.isCommon == 1}
          <label>
            <input type="radio" value="1" name="isCommon" checked="checked">是
          </label>
          <label>
            <input type="radio" value="0" name="isCommon">否
          </label>
          {@else}
          <label>
            <input type="radio" value="1" name="isCommon">是
          </label>
          <label>
            <input type="radio" value="0" name="isCommon" checked="checked">否
          </label>
          {@/if}
        </div>
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label"><span>&nbsp;</span>关联收费</label>
      <div class="col-sm-9">
        <input class="J_relatedChargeInp" type="text" data-valid-tip="请输入关联收费内容|关联收费内容不能为非数字，请重新输入" data-valid-rule="isFloat"  value="&{dlg.relatedCharge}" name="relatedCharge" />
      </div>
    </div>
  </form>
</script>
<!-- 备注内容 -->
<script type="text/template" id = "addContentTpl">
  <tr data-remark-content-id="&{contentId}" data-remark-content="&{name}">
    <input type="hidden" name="smallTagId" value="&{smallTagId}">
    <input type="hidden" name="id" value="&{contentId}">
    <input type="hidden" name="name" value="&{name}">
    <input type="hidden" name="weight" value="&{weight}">
    <input type="hidden" name="relatedCharge" value="&{relatedCharge}">
    <input type="hidden" name="isCommon" value="&{isCommon}">
    <td class="col-sm-5 J_content">&{name}</td>
    <td class="col-sm-1 J_weight">&{weight}</td>
    <td class="col-sm-3 J_relatedCharge">&{relatedCharge}</td>
    {@if isCommon == 0}
    <td class="col-sm-1 J_isCommon">否</td>
    {@else}
    <td class="col-sm-1 J_isCommon">是</td>
    {@/if}
    <td class="col-sm-2">
      <a href="javascript:;" class="label-info oper J_editRemarkContent"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      <a href="javascript:;" class="label-info oper J_delRemarkContent"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
</script>
<script type= "text/javascript">
  KISSY.ready(function(S){
    S.use('page/restaurant-management/remarks-management', function(){
      PW.page.restManagement.remarksManagement({
      });
    });
  });
</script>
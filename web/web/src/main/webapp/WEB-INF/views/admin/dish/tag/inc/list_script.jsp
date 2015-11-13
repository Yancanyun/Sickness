<%@ page contentType="text/html;charset=UTF-8" %>

<!--对话框添加模板-->
<script type="text/template" id="dlgTpl">
    <form class="form-horizontal J_addForm" action="" method="">
        <input type="hidden" name="type" value="1"/>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>上级分类</label>
            <div class="col-sm-4 J_rootClassify"></div>
        </div>
        <div class="form-group hidden">
            <label class="col-sm-3 control-label"><span class="requires">*</span>上级分类</label>
            <input class="J_bigTagInp" type="hidden" name="pId" value="" disabled="disabled"/>
            <div class="col-sm-9">
                <p class="form-control-static J_bigTagHidden"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>分类名称</label>
            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-tip="请输入菜品分类名称|分类名称不能为空，请重新输入" data-valid-rule="notNull" value="&{name}" name="name" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>上菜时限</label>
            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-tip="请输入上菜时限|上菜时限有误，请重新输入" data-valid-rule="isNumber" value="&{timeLimit}" name="timeLimit" /> 分钟(0为无限制)
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>打印排序</label>
            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-tip="请输入排序|排序不能为空，请重新输入" data-valid-rule="isNumber" value="&{weight}" name="weight" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>小票打印至</label>
            <div class="col-sm-4">
                <select class="form-control J_print" name="printerId">
                    <option value="0">菜品打印机</option>
                    <option value="1">酒水打印机</option>
                </select>
            </div>
            <div class="col-sm-3">
                <a class="form-control-static" href="#">前往>>打印机管理</a>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>是否下单立即打印</label>
            <div class="col-sm-9">
                <div class="radio">
                    <label>
                        <input type="radio" value="1" name="printAfterConfirmOrder" checked="checked">是
                    </label>
                    <label>
                        <input type="radio" value="0" name="printAfterConfirmOrder">否
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>同时打印菜的数量</label>
            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-tip="请输入数量|数量有误，请重新输入" data-valid-rule="isNumber" value="&{maxPrintNum}" name="maxPrintNum" />
            </div>
        </div>
    </form>
</script>

<!--大类渲染模板-->
<script type="text/template" id="bigTagTpl">
    <li class="clearfix" data-big-tag-id="&{id}" data-root-tag-id="&{pId}" data-root-tag-name="&{rootClassifyName}" data-big-tag-name="&{name}">
        <div class="J_inputGroup">
            <input type="hidden" name="pId" value="&{pId}" />
            <input type="hidden" name="id" value="&{id}" />
            <input type="hidden" name="name" value="&{name}" />
            <input type="hidden" name="weight" value="&{weight}" />
            <input class="J_printAfterConfirmOrder" type="hidden" name="printAfterConfirmOrder" value="&{printAfterConfirmOrder}" />
            <input type="hidden" name="maxPrintNum" value="&{maxPrintNum}" />
            <input class="J_printId" type="hidden" name="printerId" value="&{printerId}" />
            <input type="hidden" name="timeLimit" value="&{timeLimit}" />
        </div>
        <span class="root-tag">[&{rootClassifyName}]</span>
        <span class="big-tag">&{name}</span>
        <a class="J_foldToggle" href="javascript:;"></a>
        <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i class="fa fa-times"></i>&nbsp;删除大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i class="fa fa-pencil"></i>&nbsp;编辑大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i class="fa fa-plus"></i>&nbsp;添加小类</a>
        <ul class="margin-top-20 J_smallClassify open" data-big-tag-id="&{id}" data-big-tag-name="&{name}"></ul>
    </li>
</script>

<!--小类渲染模板-->
<script type="text/template" id="smallTagTpl">
    <li class="clearfix" data-small-tag-name="&{name}" data-small-tag-id="&{id}">
        <input type="hidden" name="pId" value="&{pId}" />
        <input type="hidden" name="id" value="&{id}" />
        <input type="hidden" name="name" value="&{name}" />
        <input type="hidden" name="weight" value="&{weight}" />
        <input class="J_printAfterConfirmOrderSmall" type="hidden" name="printAfterConfirmOrder" value="&{printAfterConfirmOrder}" />
        <input type="hidden" name="maxPrintNum" value="&{maxPrintNum}" />
        <input class="J_printId" type="hidden" name="printerId" value="&{printerId}" />
        <input type="hidden" name="timeLimit" value="&{timeLimit}" />
        <span class="small-tag">&{name}</span>
        <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i class="fa fa-times"></i>&nbsp;删除小类</a>
        <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i class="fa fa-pencil"></i>&nbsp;编辑小类</a>
    </li>
</script>

<!--列表渲染-->
<script type="text/template" id="listTpl">
    {@each list as it}
    <li class="clearfix" data-big-tag-id="&{it.bigTagId}" data-root-tag-id="&{it.rootTagId}" data-root-tag-name="&{it.rootTagName}" data-big-tag-name="&{it.bigTagName}">
        <div class="J_inputGroup">
            <input type="hidden" name="pId" value="&{it.rootTagId}" />
            <input type="hidden" name="id" value="&{it.bigTagId}" />
            <input type="hidden" name="name" value="&{it.bigTagName}" />
            <input type="hidden" name="weight" value="&{it.bigTagWeight}" />
            <input class="J_printAfterConfirmOrder" type="hidden" name="printAfterConfirmOrder" value="&{it.bigTagPrintAfterConfirmOrder}" />
            <input type="hidden" name="maxPrintNum" value="&{it.bigTagMaxPrintNum}" />
            <input class="J_printId" type="hidden" name="printerId" value="&{it.bigTagPrintId}" />
            <input type="hidden" name="timeLimit" value="&{it.bigTagTimeLimit}" />
            <input type="hidden" name="type" value="1" />
        </div>
        <span class="root-tag">[&{it.rootTagName}]</span>
        <span class="big-tag">&{it.bigTagName}</span>
        <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i class="fa fa-times"></i>&nbsp;删除大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i class="fa fa-pencil"></i>&nbsp;编辑大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i class="fa fa-plus"></i>&nbsp;添加小类</a>
        <ul class="margin-top-20 J_smallClassify" data-big-tag-id="&{it.bigTagId}" data-big-tag-name="&{it.bigTagName}">
            {@each it.smallTagList as item}
            <li class="clearfix" data-small-tag-name="&{item.smallTagName}" data-small-tag-id="&{item.smallTagId}">
                <input type="hidden" name="pId" value="&{it.bigTagId}" />
                <input type="hidden" name="id" value="&{item.smallTagId}" />
                <input type="hidden" name="name" value="&{item.smallTagName}" />
                <input type="hidden" name="weight" value="&{item.smallTagWeight}" />
                <input class="J_printAfterConfirmOrderSmall" type="hidden" name="printAfterConfirmOrder" value="&{item.smallTagPrintAfterConfirmOrder}" />
                <input type="hidden" name="maxPrintNum" value="&{item.smallTagMaxPrintNum}" />
                <input class="J_printId" type="hidden" name="printerId" value="&{item.smallTagPrintId}" />
                <input type="hidden" name="timeLimit" value="&{item.smallTagTimeLimit}" />
                <span class="small-tag">&{item.smallTagName}</span>
                <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i class="fa fa-times"></i>&nbsp;删除小类</a>
                <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i class="fa fa-pencil"></i>&nbsp;编辑小类</a>
            </li>
            {@/each}
        </ul>
    </li>
    {@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/dish-management/dish-classify-management', function(){
            PW.page.DishManagement.DishClassifyManagement();
        });
    });
</script>
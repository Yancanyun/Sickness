<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--对话框添加模板（小类）-->
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
                    <option value="0">不分配打印机</option>
                    <c:forEach var="printer" items="${printerList}">
                        <option value="${printer.id}">${printer.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-3">
                <a class="form-control-static" href="${website}admin/printer">前往>>打印机管理</a>
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

<!--对话框添加模板（大类）-->
<script type="text/template" id="dlgTplBig">
    <form class="form-horizontal J_addForm" autocomplete="off">
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
                    <option value="0">不分配打印机</option>
                    <c:forEach var="printer" items="${printerList}">
                        <option value="${printer.id}">${printer.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-3">
                <a class="form-control-static" href="${website}admin/printer">前往>>打印机管理</a>
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
        <div class="form-group">
            <label class="col-sm-3 control-label">选择备注</label>
            <div class="col-sm-9 J_remarkContainer">
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
            <input class="J_remarkIds" type="hidden" name="remarkId" value="&{remarkId}" />
            <input type="hidden" name="type" value="1" />
        </div>
        <span class="root-tag">[&{rootClassifyName}]</span>
        <span class="big-tag">&{name}</span>
        <a class="J_foldToggle" href="javascript:;"></a>
        <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i class="fa fa-times"></i>&nbsp;删除大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i class="fa fa-pencil"></i>&nbsp;编辑大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i class="fa fa-plus"></i>&nbsp;添加小类</a>
        <ul style="display: none" class="margin-top-20 J_smallClassify" data-big-tag-id="&{id}" data-big-tag-name="&{name}"></ul>
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
        <input type="hidden" name="type" value="1" />
        <span class="small-tag">&{name}</span>
        <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i class="fa fa-times"></i>&nbsp;删除小类</a>
        <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i class="fa fa-pencil"></i>&nbsp;编辑小类</a>
    </li>
</script>

<!--列表渲染-->
<script type="text/template" id="listTpl">
    {@each list as it}
    <li class="clearfix" data-big-tag-id="&{it.id}" data-root-tag-id="&{it.pId}" data-root-tag-name="&{it.pName}" data-big-tag-name="&{it.name}">
        <div class="J_inputGroup">
            <input type="hidden" name="pId" value="&{it.pId}" />
            <input type="hidden" name="id" value="&{it.id}" />
            <input type="hidden" name="name" value="&{it.name}" />
            <input type="hidden" name="weight" value="&{it.weight}" />
            <input class="J_printAfterConfirmOrder" type="hidden" name="printAfterConfirmOrder" value="&{it.printAfterConfirmOrder}" />
            <input type="hidden" name="maxPrintNum" value="&{it.maxPrintNum}" />
            <input class="J_printId" type="hidden" name="printerId" value="&{it.printerId}" />
            <input type="hidden" name="timeLimit" value="&{it.timeLimit}" />
            <input type="hidden" name="type" value="1" />
        </div>
        <span class="root-tag">[&{it.pName}]</span>
        <span class="big-tag">&{it.name}</span>
        <a class="J_foldToggle" href="javascript:;"></a>
        <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i class="fa fa-times"></i>&nbsp;删除大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i class="fa fa-pencil"></i>&nbsp;编辑大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i class="fa fa-plus"></i>&nbsp;添加小类</a>
        <ul style="display: none;" class="margin-top-20 J_smallClassify" data-big-tag-id="&{it.id}" data-big-tag-name="&{it.name}">
            {@each it.smallTagList as item}
            <li class="clearfix" data-small-tag-name="&{item.name}" data-small-tag-id="&{item.id}">
                <input type="hidden" name="pId" value="&{item.pId}" />
                <input type="hidden" name="id" value="&{item.id}" />
                <input type="hidden" name="name" value="&{item.name}" />
                <input type="hidden" name="weight" value="&{item.weight}" />
                <input class="J_printAfterConfirmOrderSmall" type="hidden" name="printAfterConfirmOrder" value="&{item.printAfterConfirmOrder}" />
                <input type="hidden" name="maxPrintNum" value="&{item.maxPrintNum}" />
                <input class="J_printId" type="hidden" name="printerId" value="&{item.printerId}" />
                <input type="hidden" name="timeLimit" value="&{item.timeLimit}" />
                <input type="hidden" name="type" value="1" />
                <span class="small-tag">&{item.name}</span>
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
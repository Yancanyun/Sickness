<%@ page contentType="text/html;charset=UTF-8" %>

<!--对话框添加模板-->
<script type="text/template" id="dlgTpl">
    <form class="form-horizontal J_addForm" action="" method="">
        <input type="hidden" name="pId" value="&{pId}"/>
        <input type="hidden" name="id" value="&{id}"/>

        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>上级分类</label>

            <div class="col-sm-9">
                <p class="form-control-static J_higherClassify">&{pName}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>分类名称</label>

            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-tip="请输入库存分类名称|库存名称不能为空，请重新输入" data-valid-rule="notNull"
                       value="&{name}" name="name"/>
            </div>
        </div>
    </form>
</script>

<!--大类渲染模板-->
<script type="text/template" id="bigTagTpl">
    <li class="clearfix" data-big-tag-id="&{id}" data-root-tag-id="&{pId}" data-root-tag-name="顶级分类"
        data-big-tag-name="&{name}">
        <div class="J_inputGroup">
            <input type="hidden" name="pId" value="&{pId}"/>
            <input type="hidden" name="id" value="&{id}"/>
            <input type="hidden" name="name" value="&{name}"/>
        </div>
        <span class="root-tag">[顶级分类]</span>
        <span class="big-tag">&{name}</span>
        <a href="javascript:;" class="J_foldToggle"></a>
        <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i class="fa fa-times"></i>&nbsp;删除大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i
                class="fa fa-pencil"></i>&nbsp;编辑大类</a>
        <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i class="fa fa-plus"></i>&nbsp;添加小类</a>
        <ul class="margin-top-20 J_smallClassify open" data-big-tag-id="&{id}" data-big-tag-name="&{name}"></ul>
    </li>
</script>

<!--小类渲染模板-->
<script type="text/template" id="smallTagTpl">
    <li class="clearfix" data-small-tag-name="&{name}" data-small-tag-id="&{id}">
        <input type="hidden" name="pId" value="&{pId}"/>
        <input type="hidden" name="id" value="&{id}"/>
        <input type="hidden" name="name" value="&{name}"/>
        <span class="small-tag">&{name}</span>
        <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i
                class="fa fa-times"></i>&nbsp;删除小类</a>
        <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i class="fa fa-pencil"></i>&nbsp;编辑小类</a>
    </li>
</script>

<script type="text/javascript">
    KISSY.ready(function (S) {
        S.use('page/store-management/store-classify-management', function () {
            PW.page.StoreManagement.StoreClassifyManagement();
        });
    });
</script>
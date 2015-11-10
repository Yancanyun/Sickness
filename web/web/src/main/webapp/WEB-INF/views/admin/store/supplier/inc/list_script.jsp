<%@ page contentType="text/html;charset=UTF-8" %>

<!--对话框-->
<script type="text/template" id="dlgTpl">
    <form class="form-horizontal J_addForm" action="" method="">
        <input type="hidden" value="&{id}" name="id"/>
        <input type="hidden" value="&{partyId}" name="partyId"/>

        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>名称</label>

            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-tip="请输入供应商名称|名称不能为空，请重新输入" data-valid-rule="notNull"
                       value="&{name}" name="name"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">简称</label>

            <div class="col-sm-9">
                <input class="w180" type="text" value="&{shortName}" name="shortName"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>联系人</label>

            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-rule="notNull" data-valid-tip="请输入联系人|联系人不能为空,请重新输入"
                       value="&{contactPerson}" name="contactPerson"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>联系电话</label>

            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-rule="isMobile" data-valid-tip="请输入联系电话|电话有误,请重新填写"
                       value="&{contactPhone}" name="contactPhone"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">联系地址</label>

            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-rule="isNull" data-valid-tip="请输入联系地址|地址不能为空,请重新输入"
                       value="&{address}" name="address"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">电子邮箱</label>

            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-rule="isEmail|isNull" data-valid-tip="请输入email|email有误,请重新输入"
                       value="&{email}" name="email"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">网址</label>

            <div class="col-sm-9">
                <input class="w180" type="text" data-valid-rule="isUrl|isNull" data-valid-tip="请输入网址|网址有误,请重新输入"
                       value="&{website}" name="website"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">描述</label>

            <div class="col-sm-9">
                <textarea cols="50" rows="5" data-valid-rule="isNull" data-valid-tip="请输入描述|描述不能为空,请重新输入"
                          name="description">&{description}</textarea>
            </div>
        </div>
    </form>
</script>

<!--渲染模板-->
<script type="text/template" id="renderTpl">
    <tr data-supplier-id="&{id}" data-party-id="&{partyId}">
        <td>&{name}</td>
        <td>&{shortName}</td>
        <td>&{contactPerson}</td>
        <td>&{contactPhone}</td>
        <td>&{address}</td>
        <td>&{email}</td>
        <td>&{website}</td>
        <td class="hidden description">&{description}</td>
        <td>
            <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
        </td>
    </tr>
</script>
<script type="text/javascript">
    KISSY.ready(function (S) {
        S.use('page/store-management/supplier-management', function (S) {
            PW.page.StoreManagement.SupplierManagement();
        });
    });
</script>
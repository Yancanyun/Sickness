<%@ page contentType="text/html;charset=UTF-8" %>
<!--添加菜品对话框-->
<script type="text/template" id="dlgTpl">
    <form class="form-horizontal J_addForm" action="" method="">
        <p class="count pull-right">共&nbsp;<span class="J_dishNumber">0</span>&nbsp;道菜</p>
        <p class="count pull-right">共&nbsp;￥<span class="J_dishCount">0</span></p>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>菜品</label>
            <div class="col-sm-5">
                <div id="basic"></div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>价格</label>
            <div class="col-sm-6">
                <input class="w180 J_priceInp" type="text" data-valid-rule="isFloat" data-valid-tip="请输入价格|价格有误,请重新输入" name="dishPrice" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>数量</label>
            <div class="col-sm-6">
                <input class="w180 J_numberInp" type="text" data-valid-rule="isFloat" data-valid-tip="请输入数量|数量有误,请重新输入" name="dishQuantity" value="">
            </div>
        </div>
        <div class="col-sm-offset-3 margin-bottom-15">
            <a href="javascript:;" class="J_addSpecificDish btn btn-success" disabled="disabled"><i class="fa fa-plus"></i></a>
            <a href="javascript:;" class="J_emptySpecificDish btn btn-success"><i class="fa fa-eraser"></i></a>
            <a href="javascript:;" class="J_delSpecificDish btn btn-success" disabled="disabled"><i class="fa fa-minus"></i></a>
        </div>
    </form>
    <form class="J_oper" action="" method="">
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" class="J_selectAll" />
                    </th>
                    <th>名称</th>
                    <th>单位</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="J_template"></tbody>
            </table>
        </div>
    </form>
</script>
<!--菜品模板-->
<script type="text/template" id="dishTpl">
    <tr data-dish-id="&{dishId}">
        <td><input type="checkbox" class="J_check" /></td>
        <td>&{dishName}</td>
        <td>&{dishUnit}</td>
        <td>&{dishPrice}</td>
        <td>&{dishQuantity}</td>
        <td>
            <a href="javascript:;" class="label-info J_editDish"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_delDish"><i class="fa fa-times"></i>&nbsp;删除</a>
        </td>
    </tr>
</script>
<!--菜品编辑模板-->
<script type="text/template" id="editDishTpl">
    <tr data-dish-id="&{dishId}">
        <td><input type="checkbox" class="J_check" /></td>
        <td><input class="dish-input J_dishInput" type="text" data-valid-rule="notNull" value="&{dishName}" name="dishName" /></td>
        <td><input class="dish-input J_dishInput" type="text" data-valid-rule="notNull" value="&{dishUnit}" name="dishUnit" /></td>
        <td><input class="dish-input J_dishInput" type="text" data-valid-rule="isFloat" value="&{dishPrice}" name="dishPrice" /></td>
        <td><input class="dish-input J_dishInput" type="text" data-valid-rule="isFloat" value="&{dishQuantity}" name="dishQuantity" /></td>
        <td>
            <a href="javascript:;" class="label-info J_saveDish"><i class="fa fa-save"></i>&nbsp;保存</a>
            <a href="javascript:;" class="label-info J_cancelDish"><i class="fa fa-undo"></i>&nbsp;取消</a>
        </td>
    </tr>
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/dish-management/package-dish-management-add, page/dish-management/dish-img-management-edit, page/dish-management/dish-img-management-add, widget/tab', function(S){
            PW.page.DishManagement.PackageDishManagement.Add();
            PW.page.DishManagement.DishImgManagementEdit.Core();
            PW.page.DishManagement.DishImgManagementAdd.BigPic({
                upload: {
                    // 文件接收服务端
                    uploadUrl: '${website}admin/dish/package/img/upload/ajax',
                    // 文件列表
                    fileList: '#fileList1',
                    // 选择图片按钮
                    pick: '#filePicker1',
                    // 开始上传按钮
                    ctlBtn: '#ctlBtn1',
                    fileVal: 'image'
                }
            });
            PW.page.DishManagement.DishImgManagementAdd.SmallPic({
                upload: {
                    // 文件接收服务端
                    uploadUrl: '${website}admin/dish/package/img/upload/ajax',
                    // 文件列表
                    fileList: '#fileList2',
                    // 选择图片按钮
                    pick: '#filePicker2',
                    // 开始上传按钮
                    ctlBtn: '#ctlBtn2',
                    fileVal: 'image'
                }
            });
        });
    });
</script>
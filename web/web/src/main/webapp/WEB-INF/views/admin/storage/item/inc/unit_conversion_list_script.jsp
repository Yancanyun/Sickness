<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--刷分页-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-conversion-ratio-id="&{it.id}">
        <input type="hidden" name="id" value="&{it.id}">
        <input type="hidden" name="name" value="&{it.name}">
        <input type="hidden" name="orderUnitId" value="&{it.orderUnitId}">
        <input type="hidden" name="orderToStorageRatio" value="&{it.orderToStorageRatio}">
        <input type="hidden" name="storageUnitId" value="&{it.storageUnitId}">
        <input type="hidden" name="storageToCostCardRatio" value="&{it.storageToCostCardRatio}">
        <input type="hidden" name="costCardUnitId" value="&{it.costCardUnitId}">
        <input type="hidden" name="countUnitId" value="&{it.countUnitId}">
        <td>&{it.name}</td>
        <td>&{it.orderUnitName}</td>
        <td>&{it.orderToStorageRatio}</td>
        <td>&{it.storageUnitName}</td>
        <td>&{it.storageToCostCardRatio}</td>
        <td>&{it.costCardUnitName}</td>
        <td>&{it.countUnitName}</td>
        <td>
            <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
        </td>
    </tr>
    {@/each}
</script>
<!--编辑表单-->
<script type="text/template" id="editForm">
    <form class="form-horizontal J_editForm" action="" method="">
        <input type="hidden" name="id" value="&{id}">

        <div class="form-group">
            <label class="col-sm-4 control-label"><span class="requires">*</span>物品名称</label>

            <div class="col-sm-6">
                <input class="w180" type="text" data-valid-rule="notNull" data-valid-tip="请输入物品名称|物品名称不能为空,请重新输入"
                       name="name" value="&{name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><span class="requires">*</span>订货单位</label>

            <div class="col-sm-6">
                <select class="form-control" name="orderUnitId" data-valid-rule="notNull"
                        data-valid-tip="请选择订货单位|订货单位不能为空,请重新选择">
                    <option value="">请选择</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><span class="requires">*</span>订货单位与库存单位换算</label>

            <div class="col-sm-6">
                <input class="w180" type="text" data-valid-rule="notNull" data-valid-tip="请输入订货单位与库存单位换算|换算不能为空,请重新输入"
                       name="orderToStorageRatio" value="&{orderToStorageRatio}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><span class="requires">*</span>库存单位</label>

            <div class="col-sm-6">
                <select class="form-control" name="storageUnitId" data-valid-rule="notNull"
                        data-valid-tip="请选择库存单位|库存单位不能为空,请重新选择">
                    <option value="">请选择</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><span class="requires">*</span>库存单位与成本卡单位换算</label>

            <div class="col-sm-6">
                <input class="w180" type="text" data-valid-rule="notNull" data-valid-tip="请输入库存单位与成本卡单位换算|换算不能为空,请重新输入"
                       name="storageToCostCardRatio" value="&{storageToCostCardRatio}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><span class="requires">*</span>成本卡单位</label>

            <div class="col-sm-6">
                <select class="form-control" name="costCardUnitId" data-valid-rule="notNull"
                        data-valid-tip="请选择成本卡单位|单位不能为空,请重新选择">
                    <option value="">请选择</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><span class="requires">*</span>计数单位</label>

            <div class="col-sm-6">
                <select class="form-control" name="countUnitId" data-valid-rule="notNull"
                        data-valid-tip="请选择计数单位|单位不能为空,请重新选择">
                    <option value="">请选择</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                </select>
            </div>
        </div>
        <button class="hidden" type="submit"></button>
    </form>

</script>
<script type="text/javascript">
    KISSY.ready(function (S) {
        S.use('page/store-management/store-item-management', function (S) {
            PW.page.StoreManagement.StoreItemManagement.Conversion({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '${website}admin/storage/item/unit/conversion/ajax/list',
                pageSize: 10,
                configUrl: function (url, page, me, prevPaginationData) {
//                    return url;
                    return url + '/' + page;
                }
            });
        });
    });
</script>
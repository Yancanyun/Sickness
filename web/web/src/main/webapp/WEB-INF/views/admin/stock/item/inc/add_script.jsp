<%--
  Created by IntelliJ IDEA.
  User: Karl_SC
  Date: 2017/3/17
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="tpl">
    <form class="form-horizontal J_searchForm"  style="text-align:center;" action="" method="">
        <input type="hidden" class="J_id" value="&{id}" name="id">
        <div class="form-group">
            <label class="col-sm-2"><span class="requires">*</span>规格</label>
            <div class="col-sm-6" id="basic"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-2">订货单位</label>
            <div class="col-sm-10">
                <input type="text" value="&{orderUnitId}" class="w180 col-sm-3 J_orderUnitId" readonly="readonly" name="orderUnitId">
                <label class="col-sm-4">订货到库存单位转换比例</label>
                <input type="text" value="&{orderToStorage}" class="w180 col-sm-3 J_orderToStorage" readonly="readonly" name="orderToStorage">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2">库存单位</label>
            <div class="col-sm-10">
                <input type="text" value="&{storageUnitId}" class="w180 col-sm-3 J_storageUnitId" readonly="readonly" name="storageUnitId">
                <label class="col-sm-4">库存到成本卡单位转换比例</label>
                <input type="text" value="&{storageToCost}" class="w180 col-sm-3 J_storageToCost" readonly="readonly" name="storageToCost">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2">成本卡单位</label>
            <div class="col-sm-10">
                <input type="text" value="&{costCardId}" class="w180 col-sm-3 J_costCardId" readonly="readonly" name="costCardId">
            </div>
        </div>
        <div class="margin-bottom-15">
            <a href="javasscript:;" class="btn btn-success J_add" disabled="disabled"><i class="fa fa-plus"></i>&nbsp;添加</a>
            <a href="javascript:;" class="btn btn-success J_reset"><i class="fa fa-eraser"></i>&nbsp;重置</a>
            <a href="javascript:;" class="btn btn-success J_del" disabled="disabled"><i class="fa fa-minus"></i>&nbsp;删除</a>
        </div>
    </form>
    <form class="J_tableForm" action="" method="">
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" class="J_selectAll" name="check">
                    </th>
                    <th>订货单位</th>
                    <th>转换比例</th>
                    <th>库存单位</th>
                    <th>转换比例</th>
                    <th>成本卡单位</th>
                </tr>
                </thead>
                <tbody id="J_template"></tbody>
            </table>
        </div>
    </form>
</script>
<script type="text/template" id="tableTpl">
    <tr>
        <td>
            <input type="checkbox" name="check">
            <input type="hidden" name="id" value="&{id}">
            <input type="hidden" name="orderUnitId" value="&{orderUnitId}">
            <input type="hidden" name="orderToStorage" value="&{orderToStorage}">
            <input type="hidden" name="storageUnitId" value="&{storageUnitId}">
            <input type="hidden" name="storageToCost" value="&{storageToCost}">
            <input type="hidden" name="costCardId" value="&{costCardId}">
        </td>
        <td>&{orderUnitId}</td>
        <td>&{orderToStorage}</td>
        <td>&{storageUnitId}</td>
        <td>&{storageToCost}</td>
        <td>&{costCardId}</td>
    </tr>
</script>
<script type="text/template" class="J_render">
    <div class="select-tag" data-id="&{id}" data-orderUnitId="&{orderUnitId}" data-orderToStorage="&{orderToStorage}" data-storageUnitId="&{storageUnitId}" data-storageToCost="&{storageToCost}" data-costCardId="&{costCardId}">&{orderUnitId}&{orderToStorage}&{storageUnitId}&{storageToCost}&{costCardId}<i class="fa fa-times J_delTab"></i>
        <input type="hidden" value="&{id}" name="specifications">
    </div>
</script>

<!--#include file="/pages/admin/common/footer.html"-->
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/store-management/store-item-management-add', function(S){
            PW.page.StoreManagement.StoreItemManagementAdd();
        });
    });
</script>
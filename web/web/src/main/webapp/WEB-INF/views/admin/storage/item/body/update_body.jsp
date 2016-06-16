<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">库存物品编辑</li>
        </ol>
        <h2>库存物品编辑</h2>
    </div>
    <div class="col-sm-12">
        <div class="alert hidden J_tip" role="alert">保存成功！</div>
        <form class="form-horizontal J_submitForm" action="" method="" autocomplete="off">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>编辑</h4>
                </div>
                <div class="panel-body">
                    <input class="J_storeItemId" type="hidden" name="id" value="${storageItem.id}">
                    <input class="J_isUpdated" type="hidden" name="isUpdated" value="0">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_isDisabled" data-valid-rule="length(1,30,1)" data-valid-tip="请输入1-30个字符|输入有误，请重新填写" name="name" value="${storageItem.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">助记码</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" placeholder="请输入助记码" name="assistantCode" value="${storageItem.assistantCode}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">物品编号</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" name="itemNumber" value="${storageItem.itemNumber}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>原配料</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control J_ingredient J_isDisabled" name="ingredientId">
                                <c:forEach var="ingredient" items="${ingredientList}">
                                    <option value="${ingredient.id}" <c:if test="${ingredient.id == storageItem.ingredientId}">selected="selected"</c:if> >${ingredient.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>所属分类</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control" name="tagId">
                                <c:forEach var="tag" items="${tagList}">
                                    <option value="${tag.id}" <c:if test="${tag.id == storageItem.tagId}">selected="selected"</c:if> >${tag.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>供货商</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control J_isDisabled" name="supplierPartyId">
                                <c:forEach var="supplier" items="${supplierList}">
                                    <option value="${supplier.partyId}" <c:if test="${supplier.partyId == storageItem.supplierPartyId}">selected="selected"</c:if> >${supplier.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control J_unitType J_orderUnitType J_isDisabled">
                                <option value="1" <c:if test="${orderUnitType == 1}">selected="selected"</c:if>>重量单位</option>
                                <option value="2" <c:if test="${orderUnitType == 2}">selected="selected"</c:if>>数量单位</option>
                            </select>
                            <select class="w180 form-control J_orderUnit J_isDisabled" name="orderUnitId">
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}" <c:if test="${unit.id == storageItem.orderUnitId}">selected="selected"</c:if> data-unit-name="${unit.name}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="w180 form-control hidden J_orderUnit J_isDisabled" name="orderUnitId" disabled="disabled">
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}" <c:if test="${unit.id == storageItem.orderUnitId}"> selected="selected" </c:if> data-unit-name="${unit.name}">${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位与库存换算关系</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_isDisabled" data-valid-rule="isFloat" data-valid-tip="请输入订货单位与库存转换关系|转换关系有误，请重新填写" name="orderToStorageRatio" value="${storageItem.orderToStorageRatio}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control J_unitType J_storageUnitType J_isDisabled">
                                <option value="1" <c:if test="${storageUnitType == 1}">selected="selected"</c:if>>重量单位</option>
                                <option value="2" <c:if test="${storageUnitType == 2}">selected="selected"</c:if>>数量单位</option>
                            </select>
                            <select class="w180 form-control  J_storageUnit J_isDisabled" name="storageUnitId">
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}" <c:if test="${unit.id == storageItem.storageUnitId}">selected="selected"</c:if>>${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="w180 form-control hidden J_storageUnit J_isDisabled" name="storageUnitId" disabled="disabled">
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}" <c:if test="${unit.id == storageItem.storageUnitId}">selected="selected"</c:if>>${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位与成本卡换算关系</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_isDisabled J_storageToCostRatio" data-valid-rule="isFloat" data-valid-tip="请输入库存单位与成本卡换算关系|转换关系有误，请重新填写" name="storageToCostCardRatio" value="${storageItem.storageToCostCardRatio}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">成本卡单位</label>
                        <div class="col-sm-6">
                            <input type="hidden" name="costCardUnitId" value="${storageItem.costCardUnitId}">
                            <input type="text" class="w180 J_costCardUnit" value="${storageItem.costCardUnitName}" readonly="readonly">

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>计数单位</label>
                        <div class="col-sm-6">
                            <input type="hidden" name="countUnitId" value="${storageItem.orderUnitId}">
                            <input type="text" class="w180 J_countUnit" value="${storageItem.orderUnitName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存预警上限</label>
                        <div class="col-sm-9">
                            <input type="text" class="w180 J_storageWarning J_maxStorageWarning" data-valid-rule="isFloat" data-valid-tip="请输入库存预警上限|输入有误，请重新填写" name="maxStorageQuantity" value="${storageItem.maxStorageQuantity}">
                            <input type="text" class="w50 J_storageRelatedUnit J_storageWarningUnit" readonly value="${storageItem.storageUnitName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存预警下限</label>
                        <div class="col-sm-9">
                            <input type="text" class="w180 J_storageWarning J_minStorageWarning" data-valid-rule="isFloat" data-valid-tip="请输入库存预警下限|输入有误，请重新填写" name="minStorageQuantity" value="${storageItem.minStorageQuantity}">
                            <input type="text" class="w50 J_storageRelatedUnit J_storageWarningUnit" readonly value="${storageItem.storageUnitName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">最新入库价格</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" name="lastStockInPrice" value="${storageItem.lastStockInPrice}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">出库方式</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control" name="stockOutType">
                                <option value="1" <c:if test="${storageItem.stockOutType == 1}">selected="selected"</c:if>>加权平均(自动)</option>
                                <option value="2" <c:if test="${storageItem.stockOutType == 2}">selected="selected"</c:if>>手动</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">入库总数量</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_totalQuantity" name="totalQuantity" value="${storageItem.totalStockInQuantity}" readonly="readonly">
                            <input type="text" class="w50 J_storageRelatedUnit" readonly value="${storageItem.storageUnitName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">入库总金额</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" name="totalMoney" value="${storageItem.totalStockInMoney}" readonly="readonly">
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <div class="btn-toolbar">
                                <button class="btn btn-primary J_submit" type="button"><i class="fa fa-save"></i>&nbsp;保存</button>
                                <button type="reset" class="btn btn-default"><i class="fa fa-undo"></i>&nbsp;重置</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
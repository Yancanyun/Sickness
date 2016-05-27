<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">原配料添加</li>
        </ol>
        <h2>原配料添加</h2>
    </div>
    <div class="col-sm-12">
        <c:if test="${!empty msg}">
        <div class="alert hidden J_tip" role="alert">提示信息!</div>
        </c:if>
        <form class="form-horizontal J_submitForm" action="" method="" autocomplete="off">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>添加原配料</h4>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_name" placeholder="请输入原配料名称" data-valid-rule="length(1,30,1)" data-valid-tip="请输入1-30个字符|输入有误，请重新填写" name="name" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">助记码</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" placeholder="请输入助记码" name="assistantCode" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>所属分类</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control" name="tagId">
                                <c:forEach var="tag" items="${tagList}">
                                    <option value="${tag.id}">${tag.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control J_unitType">
                                <option value="1">重量单位</option>
                                <option value="2">数量单位</option>
                            </select>
                            <select class="w180 form-control" name="orderUnitId">
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="w180 form-control hidden" name="orderUnitId" disabled="disabled">
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位与库存换算关系</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" data-valid-rule="isFloat" data-valid-tip="请输入订货单位与库存转换关系|转换关系有误，请重新填写" name="orderToStorageRatio" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control J_unitType J_storageUnitType">
                                <option value="1">重量单位</option>
                                <option value="2">数量单位</option>
                            </select>
                            <select class="w180 form-control J_storageUnit" name="storageUnitId">
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="w180 form-control hidden J_storageUnit" name="storageUnitId" disabled="disabled">
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位与成本卡换算关系</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" data-valid-rule="isFloat" data-valid-tip="请输入库存单位与成本卡换算关系|转换关系有误，请重新填写" name="storageToCostCardRatio" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>成本卡单位</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control J_unitType">
                                <option value="1">重量单位</option>
                                <option value="2">数量单位</option>
                            </select>
                            <select class="w180 form-control" name="costCardUnitId">
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="w180 form-control hidden" name="costCardUnitId" disabled="disabled">
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存预警上限</label>
                        <div class="col-sm-9">
                            <input type="text" class="w180 J_storageWarning" data-valid-rule="isFloat" data-valid-tip="请输入库存预警上限|输入有误，请重新填写" name="maxStorageQuantity" value="">
                            <input type="text" class="w50 J_storageWarningUnit" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存预警下限</label>
                        <div class="col-sm-9">
                            <input type="text" class="w180 J_storageWarning" data-valid-rule="isFloat" data-valid-tip="请输入库存预警下限|输入有误，请重新填写" name="minStorageQuantity" value="">
                            <input type="text" class="w50 J_storageWarningUnit" readonly>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <div class="btn-toolbar">
                                <button class="btn btn-primary J_submit" type="submit"><i class="fa fa-save"></i>&nbsp;保存</button>
                                <button type="reset" class="btn btn-default"><i class="fa fa-undo"></i>&nbsp;重置</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
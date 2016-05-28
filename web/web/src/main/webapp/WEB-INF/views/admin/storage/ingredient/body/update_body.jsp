
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">原配料编辑</li>
        </ol>
        <h2>原配料编辑</h2>
    </div>
    <div class="col-sm-12">
        <div class="alert hidden J_tip" role="alert">保存成功！</div>
        <form class="form-horizontal J_submitForm" action="" method="" autocomplete="off">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>编辑</h4>
                </div>
                <div class="panel-body">
                    <input class="J_ingredientId" type="hidden" name="id" value="${ingredient.id}">
                    <input class="J_isUpdated" type="hidden" name="isUpdated" value="0">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_name" placeholder="请输入原配料名称" data-valid-rule="length(1,30,1)" data-valid-tip="请输入1-30个字符|输入有误，请重新填写" name="name" value="${ingredient.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">原配料编号</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" name="ingredientNumber" value="${ingredient.ingredientNumber}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">助记码</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" placeholder="请输入助记码" name="assistantCode" value="${ingredient.assistantCode}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>所属分类</label>
                        <div class="col-sm-6">
                            <select class="w180 form-control" name="tagName">
                                <c:forEach var="tag" items="${tagList}">
                                    <option value="${tag.id}" <c:if test="${tag.id == storageItem.tagId}">selected="selected"</c:if> >${tag.name}</option>
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
                                <option value="aa" data-unit-name="aa">aa</option>
                                <option value="bb" data-unit-name="bb">bb</option>
                                <option value="cc" data-unit-name="cc">cc</option>
                            </select>
                            <select class="w180 form-control hidden" name="orderUnitId" disabled="disabled">
                                <option value="dd" data-unit-name="dd">dd</option>
                                <option value="ee" data-unit-name="ee">ee</option>
                                <option value="ff" data-unit-name="ff">ff</option>
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
                                <option value="aa" data-unit-name="aa">aa</option>
                                <option value="bb" data-unit-name="bb">bb</option>
                                <option value="cc" data-unit-name="cc">cc</option>
                            </select>
                            <select class="w180 form-control hidden J_storageUnit" name="storageUnitId" disabled="disabled">
                                <option value="dd" data-unit-name="dd">dd</option>
                                <option value="ee" data-unit-name="ee">ee</option>
                                <option value="ff" data-unit-name="ff">ff</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位与成本卡换算关系</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_storageToCostRatio" data-valid-rule="isFloat" data-valid-tip="请输入库存单位与成本卡换算关系|转换关系有误，请重新填写" name="storageToCostCardRatio" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">成本卡单位</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" name="costCardUnitId" value="" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存预警上限</label>
                        <div class="col-sm-9">
                            <input type="text" class="w180 J_storageWarning" data-valid-rule="isFloat" data-valid-tip="请输入库存预警上限|输入有误，请重新填写" name="maxStorageQuantity" value="">
                            <input type="text" class="w50 J_storageWarningUnit" readonly value="aa">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存预警下限</label>
                        <div class="col-sm-9">
                            <input type="text" class="w180 J_storageWarning" data-valid-rule="isFloat" data-valid-tip="请输入库存预警下限|输入有误，请重新填写" name="minStorageQuantity" value="">
                            <input type="text" class="w50 J_storageWarningUnit" readonly value="aa">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">物品均价</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_averagePrice" name="averagePrice" value="" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">结存</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_realQuantity" name="realQuantity" value="" readonly="readonly">
                            <input type="text" class="w50" readonly value="aa">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">结存金额</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" name="realMoney" value="" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">总数量</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180 J_totalQuantity" name="totalQuantity" value="" readonly="readonly">
                            <input type="text" class="w50" readonly value="aa">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">总金额</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" name="totalMoney" value="" readonly="readonly">
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <div class="btn-toolbar">
                                <button class="btn btn-primary J_submit" type="submit"><i class="fa fa-save"></i>&nbsp;保存</button>
                                <button class="btn btn-default" onclick="history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
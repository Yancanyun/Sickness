<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">库存物品管理</li>
        </ol>
        <h2>库存物品管理-添加库存物品</h2>
    </div>
    <div class="col-sm-12 margin-bottom-30">
        <form class="form-horizontal J_operForm" action="${website}admin/storage/item" method="post">
            <c:if test="${!empty msg}">
                <div class="alert alert-danger J_tip" role="alert">提示信息!</div>
            </c:if>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>添加库存物品</h4>
                </div>
                <div class="panel-body">

                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>名称</label>

                        <div class="col-sm-6">
                            <input class="w180 J_name" type="text" data-valid-rule="notNull"
                                   data-valid-tip="请输入库存物品名称|名称不能为空,请重新输入" value="" name="name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">编号</label>

                        <div class="col-sm-6">
                            <input class="w180" type="text" value="" name="itemNumber"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">助记码</label>

                        <div class="col-sm-6">
                            <input class="w180 J_assistantCode" type="text" value="" name="assistantCode"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>供货商</label>

                        <div class="col-sm-6">
                            <select class="form-control w180" data-valid-rule="notNull" data-valid-tip="请选择供货商|供货商不能为空,请重新选择"
                                    name="supplierPartyId">
                                <c:forEach var="supplier" items="${supplierList}">
                                    <option value="${supplier.partyId}">${supplier.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>所属分类</label>

                        <div class="col-sm-6">
                            <select class="form-control w180" data-valid-rule="notNull" data-valid-tip="请选择所属分类|分类不能为空,请重新选择"
                                    name="tagId">
                                <c:forEach var="tag" items="${tagList}">
                                    <option value="${tag.id}">${tag.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位</label>

                        <div class="col-sm-9">
                            <select class="form-control J_orderUnitType w180">
                                <option value="1">重量单位</option>
                                <option value="2">数量单位</option>
                            </select>
                            <select class="form-control w180" name="orderUnitId">
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="form-control hidden w180" name="orderUnitId" disabled="disabled">
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位与库存换算关系</label>

                        <div class="col-sm-6">
                            <input class="w180" type="text" data-valid-tip="请输入订货单位与库存换算关系|换算关系有误，请重新输入"
                                   data-valid-rule="isFloat" value="1" name="orderToStorageRatio"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位</label>

                        <div class="col-sm-9">
                            <select class="form-control J_orderUnitType w180">
                                <option value="1">重量单位</option>
                                <option value="2">数量单位</option>
                            </select>
                            <select class="form-control w180" name="storageUnitId">
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="form-control hidden w180" name="storageUnitId" disabled="disabled">
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位与成本卡换算关系</label>

                        <div class="col-sm-6">
                            <input class="w180" type="text" data-valid-tip="请输入库存单位与成本卡换算关系|换算关系有误，请重新输入"
                                   data-valid-rule="isFloat" value="1" name="storageToCostCardRatio"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>成本卡单位</label>

                        <div class="col-sm-9">
                            <select class="form-control J_orderUnitType w180">
                                <option value="1">重量单位</option>
                                <option value="2">数量单位</option>
                            </select>
                            <select class="form-control w180" name="costCardUnitId">
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="form-control hidden w180" name="costCardUnitId" disabled="disabled">
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">计数单位</label>

                        <div class="col-sm-9">
                            <select class="form-control J_orderUnitType w180">
                                <option value="1">重量单位</option>
                                <option value="2">数量单位</option>
                            </select>
                            <select class="form-control w180" name="countUnitId">
                                <option value="0">请选择</option>
                                <c:forEach var="unit" items="${weightUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <select class="form-control hidden w180" name="countUnitId" disabled="disabled">
                                <option value="0">请选择</option>
                                <c:forEach var="unit" items="${quantityUnit}">
                                    <option value="${unit.id}">${unit.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>最大库存量</label>

                        <div class="col-sm-6">
                            <input class="w180" type="text" data-valid-tip="请输入最大库存量|最大库存量有误，请重新输入"
                                   data-valid-rule="isFloat" value="0" name="maxStorageQuantity"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>最小库存量</label>

                        <div class="col-sm-6">
                            <input class="w180" type="text" data-valid-tip="请输入最大库存量|最大库存量有误，请重新输入"
                                   data-valid-rule="isFloat" value="0" name="minStorageQuantity"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">出库方式</label>

                        <div class="col-sm-6">
                            <select class="form-control w180" name="stockOutType">
                                <option value="1" selected="selected">加权平均(自动)</option>
                                <option value="2">手动</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <div class="btn-toolbar">
                                <button class="btn-primary btn J_submitBtn" type="submit" data-btn-type="loading"
                                        data-btn-loading-text="正在保存，请稍后...">
                                    <i class="fa fa-save"></i>
                                    &nbsp;保存
                                </button>
                                <button class="btn-default btn" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
                                <!--编辑用下面这个-->
                                <!--<button class="btn-default btn" type="button" onclick="history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回</button>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
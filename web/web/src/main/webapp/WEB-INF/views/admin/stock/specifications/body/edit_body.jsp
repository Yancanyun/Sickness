<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 17/3/8
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
  <div class="row">
    <div class="col-sm-12">
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
        <li><a href="#">规格管理</a></li>
        <li class="active">规格类型编辑</li>
      </ol>
      <h2>规格类型编辑</h2>
    </div>
    <div class="col-sm-12">
      <div class="alert hidden J_tip" role="alert">保存成功！</div>
      <form class="form-horizontal J_submitForm" action="" method="" autocomplete="off">
        <div class="panel panel-info">
          <div class="panel-heading">
            <h4>编辑</h4>
          </div>
          <div class="panel-body">
            <input class="J_id" type="hidden" name="id" value="112233">
            <div class="form-group">
              <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位</label>
              <div class="col-sm-6">
                <select class="w180 form-control J_unitType J_orderUnitType">
                  <option value="1" <c:if test="${orderUnitType == 1}">selected="selected"</c:if>>重量单位</option>
                  <option value="2" <c:if test="${orderUnitType == 2}">selected="selected"</c:if>>数量单位</option>
                </select>
                <select class="w180 form-control J_orderUnit" name="orderUnitId">
                  <c:forEach items="${weightUnit}" var="unit">
                    <option value="${unit.id}" <c:if test="${unit.id == specification.orderUnitId}">selected="selected"</c:if>>${unit.name}</option>
                  </c:forEach>
                </select>
                <select class="w180 form-control hidden J_orderUnit" name="orderUnitId" disabled="disabled">
                  <c:forEach items="${quantityUnit}" var="unit">
                    <option value="${unit.id}" <c:if test="${unit.id == specification.orderUnitId}">selected="selected"</c:if>>${unit.name}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位与库存换算关系</label>
              <div class="col-sm-6">
                <input type="text" class="w180" data-valid-rule="isFloat" data-valid-tip="请输入订货单位与库存转换关系|转换关系有误，请重新填写" name="orderToStorageRatio" value="${specification.orderToStorage}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位</label>
              <div class="col-sm-6">
                <select class="w180 form-control J_unitType J_storageUnitType">
                  <option value="1" <c:if test="${storageUnitType == 1}">selected="selected"</c:if>>重量单位</option>
                  <option value="2" <c:if test="${storageUnitType == 2}">selected="selected"</c:if>>数量单位</option>
                </select>
                <select class="w180 form-control J_storageUnit" name="storageUnitId">
                  <c:forEach items="${weightUnit}" var="unit">
                    <option value="${unit.id}" <c:if test="${unit.id == specification.storageUnitId}">selected="selected"</c:if>>${unit.name}</option>
                  </c:forEach>
                </select>
                <select class="w180 form-control hidden J_storageUnit" name="storageUnitId" disabled="disabled">
                  <c:forEach items="${quantityUnit}" var="unit">
                    <option value="${unit.id}" <c:if test="${unit.id == specification.storageUnitId}">selected="selected"</c:if>>${unit.name}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label"><span class="requires">*</span>库存单位与成本卡换算关系</label>
              <div class="col-sm-6">
                <input type="text" class="w180" data-valid-rule="isFloat" data-valid-tip="请输入库存单位与成本卡换算关系|转换关系有误，请重新填写" name="storageToCostCardRatio" value="${specification.storageToCost}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label"><span class="requires">*</span>成本卡单位</label>
              <div class="col-sm-6">
                <select class="w180 form-control J_unitType">
                  <option value="1" <c:if test="${costCardUnitType == 1}">selected="selected"</c:if>>重量单位</option>
                  <option value="2" <c:if test="${costCardUnitType == 2}">selected="selected"</c:if>>数量单位</option>
                </select>
                <select class="w180 form-control" name="costCardUnitId">
                  <c:forEach items="${weightUnit}" var="unit">
                    <option value="${unit.id}" <c:if test="${unit.id == specification.costCardUnitId}">selected="selected"</c:if>>${unit.name}</option>
                  </c:forEach>
                </select>
                <select class="w180 form-control hidden" name="costCardUnitId" disabled="disabled">
                  <c:forEach items="${quantityUnit}" var="unit">
                    <option value="${unit.id}" <c:if test="${unit.id == specification.costCardUnitId}">selected="selected"</c:if>>${unit.name}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <div class="row">
              <div class="col-sm-6 col-sm-offset-3">
                <div class="btn-toolbar">
                  <button class="btn btn-primary J_submit" type="button"><i class="fa fa-save"></i>&nbsp;保存</button>
                  <button class="btn btn-default" onclick="history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>

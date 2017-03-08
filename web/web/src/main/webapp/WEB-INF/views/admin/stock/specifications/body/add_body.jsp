<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 17/3/8
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
  <div class="row">
    <div class="col-sm-12">
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
        <li><a href="#">规格管理</a></li>
        <li class="active">规格类型添加</li>
      </ol>
      <h2>规格类型添加</h2>
    </div>
    <div class="col-sm-12">
      <div class="alert hidden J_tip" role="alert">保存成功！</div>
      <form class="form-horizontal J_submitForm" action="" method="" autocomplete="off">
        <div class="panel panel-info">
          <div class="panel-heading">
            <h4>添加</h4>
          </div>
          <div class="panel-body">
            <div class="form-group">
              <label class="col-sm-3 control-label"><span class="requires">*</span>订货单位</label>
              <div class="col-sm-6">
                <select class="w180 form-control J_unitType J_orderUnitType">
                  <option value="1">重量单位</option>
                  <option value="2">数量单位</option>
                </select>
                <select class="w180 form-control J_orderUnit" name="orderUnitId">
                  <option value="aa" data-unit-name="aa">aa</option>
                  <option value="bb" data-unit-name="bb">bb</option>
                  <option value="cc" data-unit-name="cc">cc</option>
                </select>
                <select class="w180 form-control hidden J_orderUnit" name="orderUnitId" disabled="disabled">
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
                  <option value="aa" data-unit-name="aa">aa</option>
                  <option value="bb" data-unit-name="bb">bb</option>
                  <option value="cc" data-unit-name="cc">cc</option>
                </select>
                <select class="w180 form-control hidden" name="costCardUnitId" disabled="disabled">
                  <option value="dd" data-unit-name="dd">dd</option>
                  <option value="ee" data-unit-name="ee">ee</option>
                  <option value="ff" data-unit-name="ff">ff</option>
                </select>
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
</div>

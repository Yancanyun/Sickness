<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">基本信息管理</a></li>
      <li class="active">打印机管理</li>
    </ol>
    <h2>打印机管理</h2>
  </div>
  <div class="col-sm-12">
    <form class="form-horizontal J_addForm" action="${website}admin/printer" method="post">
      <c:if test="${!empty msg}">
        <div class="alert alert-danger J_tip" role="alert">${msg}</div>
      </c:if>
      <div class="panel panel-info">
        <div class="panel-heading">
          <h4>添加</h4>
        </div>
        <div class="panel-body clearfix">
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>打印机名称</label>
            <div class="col-sm-6">
              <input class="w180 form-control" type="text" data-valid-tip="请输入长度为1~15个字的名称|名称有误，请重新输入" data-valid-rule="length(0,16)" value="" name="name" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>打印机品牌</label>
            <div class="col-sm-8">
              <select class="w180 form-control J_select J_brand" value="" name="brand">
                <option value="-1">请选择</option>
                <c:forEach var="brand" items="${brandList}">
                  <option value="${brand.id}">${brand.description}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>打印机型号</label>
            <div class="col-sm-8">
              <select class="w180 form-control J_select J_printerModel" value="" name="printerModel">
                <option value="-1">请选择</option>
                <c:forEach var="model" items="${modelList}">
                  <option value="${model.id}">${model.description}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>打印机类型</label>
            <div class="col-sm-8">
              <select class="w180 form-control J_select J_type" value="" name="type">
                <option value="-1">请选择</option>
                <c:forEach var="type" items="${typeList}">
                  <option value="${type.id}">${type.description}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group classify-details J_classifyDetails">
            <label class="col-sm-3 control-label"></label>
            <div class="col-sm-6">
              <div class="checkbox block">
                <c:forEach var="dishTag" items="${availableDishTag}">
                  <label>
                    <input type="checkbox" value="${dishTag.id}" name="dishTagList"> ${dishTag.name}
                  </label>
                </c:forEach>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">设备编号</label>
            <div class="col-sm-6">
              <input class="w180 form-control" type="text" data-valid-tip="请输入长度为0~15个字符的设备编号|设备编号有误，请重新输入" data-valid-rule="isNull|length(0,16)" value="" name="deviceNumber" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>IP地址</label>
            <div class="col-sm-6">
              <input class="w180 form-control" type="text" data-valid-tip="请输入IP地址|IP地址有误，请重新输入" data-valid-rule="isIP" value="" name="ipAddress" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>是否停用</label>
            <div class="col-sm-6">
              <div class="radio block">
                <label>
                  <input type="radio" value="0" name="state" checked> 否
                </label>
                <label>
                  <input type="radio" value="1" name="state"> 是
                </label>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>是否收银打印机</label>
            <div class="col-sm-6">
              <div class="radio block">
                <label>
                  <input type="radio" value="0" name="isCashierPrinter" checked> 否
                </label>
                <label>
                  <input type="radio" value="1" name="isCashierPrinter"> 是
                </label>
              </div>
            </div>
          </div>
        </div>
        <div class="panel-footer">
          <div class="row">
            <div class="col-sm-6 col-sm-offset-3">
              <div class="btn-toolbar">
                <button class="btn-primary btn J_submitBtn" type="submit" data-btn-type="loading" data-btn-loading-text="正在保存，请稍后">
                  <i class="fa fa-save"></i>
                  &nbsp;保存
                </button>
                <button class="btn-default btn" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
</div>


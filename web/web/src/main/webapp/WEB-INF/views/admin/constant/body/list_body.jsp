<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a>
      </li>
      <li>
        <a href="#">基本信息管理</a>
      </li>
      <li class="active">全局设置</li>
    </ol>
    <h2>全局配置</h2>
    <c:if test="${!empty successMsg}">
      <div class="alert alert-success col-sm-12 J_msg" role="alert">${successMsg}</div>
    </c:if>
    <c:if test="${!empty failedMsg}">
      <div class="alert alert-danger col-sm-12 J_msg" role="alert">${failedMsg}</div>
    </c:if>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>设置列表</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_operForm" action="${website}admin/constant" method="POST">
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>内网IP(非内网的用户将不能访问顾客点餐页)</label>
            <div class="col-sm-6">
              <input class="w180" type="text" value="${internalNetworkAddress}" name="internalNetworkAddress" data-valid-rule="isIP"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>菜品打印机最多可打印的菜品数量</label>
            <div class="col-sm-6">
              <input class="w180" type="text" value="${printerPrintMaxNum}" name="printerPrintMaxNum" data-valid-rule="isPositiveIngeter"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>是否启用智能排菜</label>
            <div class="col-sm-6">
              <div class="radio block">
                <label>
                  <input type="radio" value="1" name="autoPrintOrderDishStartStatus" <c:if test="${autoPrintOrderDishStartStatus == 1}">checked=""</c:if>> 是
                </label>
                <label>
                  <input type="radio" value="0" name="autoPrintOrderDishStartStatus" <c:if test="${autoPrintOrderDishStartStatus == 0}">checked=""</c:if>> 否
                </label>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>库存计算时是否启用四舍五入</label>
            <div class="col-sm-6">
              <div class="radio block">
                <label>
                  <input type="radio" value="1" name="roundingMode" <c:if test="${roundingMode == 1}">checked=""</c:if>> 是
                </label>
                <label>
                  <input type="radio" value="0" name="roundingMode" <c:if test="${roundingMode == 0}">checked=""</c:if>> 否
                </label>
              </div>
            </div>
          </div>
        </form>
      </div>
      <div class="panel-footer">
        <div class="row">
          <div class="col-sm-6 col-sm-offset-3">
            <div class="btn-toolbar">
              <button class="btn-primary btn J_submitBtn" type="submit" data-btn-type="loading" data-btn-loading-text="正在保存，请稍后">
                <i class="fa fa-save"></i>&nbsp;保存
              </button>
              <button class="btn-default btn" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">会员管理</a></li>
      <li class="active">会员等级添加</li>
    </ol>
    <h2>会员管理-添加等级</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-danger col-sm-12 J_msg" role="alert">${msg}</div>
    </c:if>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>添加会员等级</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_gradeForm" method="post" action="${website}admin/vip/grade/new" autocomplete="off">
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>会员价方案
            </label>
            <div class="col-sm-6">
              <select class="form-control J_PlanSelect w180" name="vipDishPricePlanId">
                <option value="-1">请选择</option>
                <c:forEach var="vipDishPricePlan" items="${vipDishPricePlanList}">
                <option value="${vipDishPricePlan.id}">${vipDishPricePlan.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>会员等级名称
            </label>
            <div class="col-sm-6">
              <input class="w180 J_tableName" type="text" name="name" value="" data-valid-tip="请输入会员等级名称|会员等级不能为空，请重新输入" data-valid-rule="notNull"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>最低消费金额
            </label>
            <div class="col-sm-6">
              <input class="w180" type="text" name="minConsumption" value="" data-valid-tip="请输入最低消费金额|最低消费金额不符合规格，请重新输入"  data-valid-rule="notNull&isFloat"/>&nbsp;元
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>信用额度
            </label>
            <div class="col-sm-6">
              <input class="w180" type="text" name="creditAmount" value="" data-valid-tip="请输入信用额度|信用额度不符合规格，请重新输入" data-valid-rule="notNull&isFloat"/>&nbsp;元
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>结算周期
            </label>
            <div class="col-sm-6">
              <input class="w180" type="text" name="settlementCycle" value="" data-valid-tip="请输入结算周期|结算周期不符合规格，请重新输入" data-valid-rule="notNegativeNumber"/>&nbsp;月
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>升级预提醒额度
            </label>
            <div class="col-sm-6">
              <input class="w180" type="text" name="preReminderAmount" value="" data-valid-tip="请输入升级预提醒额度|升级预提醒额度不符合规格，请重新输入" data-valid-rule="notNull&isFloat"/>&nbsp;元
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">卡片政策</label>
            <div class="col-sm-6">
              <textarea class="w180" name="cardPolicy"></textarea>
            </div>
          </div>
          <div class="panel-footer">
            <div class="row">
              <div class="col-sm-6 col-sm-offset-3">
                <div class="btn-toolbar">
                  <button class="btn-primary btn J_save" type="submit"  data-btn-type="loading" data-btn-loading-text="正在保存，请稍后">
                    <i class="fa fa-save"></i>
                    &nbsp;保存
                  </button>
                  <button class="btn-default btn J_reset" type="reset">
                    <i class="fa fa-undo"></i>
                    &nbsp;重置
                  </button>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div><!--row-->
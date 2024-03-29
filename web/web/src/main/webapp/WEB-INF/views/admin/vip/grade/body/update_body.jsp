<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">会员管理</a></li>
      <li class="active">会员等级编辑</li>
    </ol>
    <h2>会员管理-编辑等级</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-danger col-sm-12 J_msg" role="alert">${msg}</div>
    </c:if>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>编辑会员等级</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_gradeForm" method="post" action="${website}admin/vip/grade/update/${vipGrade.id}" autocomplete="off">
          <input class="J_gradeId" type="hidden" name="id" value="${vipGrade.id}">
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>会员价方案
            </label>
            <div class="col-sm-6">
              <select class="form-control J_PlanSelect w180" name="vipDishPricePlanId">
                <option value="0">不使用会员价方案</option>
                <c:forEach var="vipDishPricePlan" items="${vipDishPricePlanList}">
                    <option value="${vipDishPricePlan.id}" <c:if test="${vipDishPricePlan.id == vipGrade.vipDishPricePlanId}">selected="selected"</c:if>>${vipDishPricePlan.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>会员等级名称
            </label>
            <div class="col-sm-6">
              <input class="w180 J_tableName" type="text" name="name" value="${vipGrade.name}" data-valid-tip="请输入会员等级名称|会员等级不能为空，请重新输入" data-valid-rule="notNull"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>最低消费金额
            </label>
                <div class="col-sm-6">
                  <c:choose>
                    <c:when  test="${vipGrade.id eq 1}">
                      <input class="w180" type="text" name="minConsumption" value="${vipGrade.minConsumption}" readonly/>&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;本条数据为系统默认，无法修改
                    </c:when>
                    <c:otherwise>
                      <input class="w180 J_minConsumption" type="text" name="minConsumption" value="${vipGrade.minConsumption}"/>&nbsp;元
                    </c:otherwise>
                  </c:choose>
                </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>信用额度
            </label>
            <div class="col-sm-6">
              <input class="w180 J_creditAmount" type="text" name="creditAmount" value="${vipGrade.creditAmount}" data-valid-tip="请输入信用额度|信用额度不符合规格，请重新输入" data-valid-rule="notNull&isFloat" />&nbsp;元
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>结算周期
            </label>
            <div class="col-sm-6">
              <input class="w180 J_settlementCycle" type="text" name="settlementCycle" value="${vipGrade.settlementCycle}" data-valid-tip="请输入结算周期|结算周期不符合规格，请重新输入"/>&nbsp;月
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>升级预提醒额度
            </label>
                <div class="col-sm-6">
                  <c:choose>
                    <c:when  test="${vipGrade.id eq 1}">
                      <input class="w180" type="text" name="preReminderAmount" value="${vipGrade.preReminderAmount}" readonly />&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;本条数据为系统默认，无法修改
                    </c:when>
                    <c:otherwise>
                      <input class="w180" type="text" name="preReminderAmount" value="${vipGrade.preReminderAmount}" data-valid-tip="请输入升级预提醒额度|升级预提醒额度不符合规格，请重新输入" data-valid-rule="notNull&isFloat"/>&nbsp;元
                    </c:otherwise>
                  </c:choose>
                </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">卡片政策</label>
            <div class="col-sm-6">
              <textarea class="w180" name="cardPolicy">${vipGrade.cardPolicy}</textarea>
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
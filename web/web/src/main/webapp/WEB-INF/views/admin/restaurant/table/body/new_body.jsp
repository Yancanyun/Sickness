<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">饭店管理</a></li>
      <li class="active">餐台管理</li>
    </ol>
    <h2>餐台管理-添加餐台</h2>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>添加餐台信息</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_tableForm" method="POST" action="${website}admin/restaurant/table/new">
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>餐台区域
            </label>
            <div class="col-sm-2">
              <select class="form-control J_tableArea" name="areaId">
                <option value="-1">请选择</option>
                <c:forEach var="area" items="${areaList}">
                  <option value="${area.id}">${area.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-6 control-label">
              <span class="requires">*</span>餐台名称
            </label>
            <div class="col-sm-6">
              <input class="w180 J_tableName" type="text" name="name" value="" data-valid-tip="请输入餐台名称|餐台名称不能为空，请重新输入" data-valid-rule="notNull&isRepeat"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>标准座位数
            </label>
            <div class="col-sm-6">
              <input class="w180" type="text" name="seatNum" value="" data-valid-tip="请输入标准座位数|标准座位数不符合规格，请重新输入" data-valid-rule="notNull&isNumber"/>&nbsp;位
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>餐位费用
            </label>
            <div class="col-sm-6">
              <input class="w180" type="text" name="seatFee" value="" data-valid-tip="请输入餐位费用|餐位费用不符合规格，请重新输入" data-valid-rule="notNull&isFloat"/>&nbsp;元/每人
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>餐台费用
            </label>
            <div class="col-sm-6">
              <input class="w180" type="text" name="tableFee" value="" data-valid-tip="请输入餐台费用|餐台费用不符合规格，请重新输入" data-valid-rule="notNull&isFloat"/>&nbsp;元
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>最低消费
            </label>
            <div class="col-sm-6">
              <input class="w180" type="text" name="minCost" value="" data-valid-tip="请输入最低消费|最低消费不符合规格，请重新输入" data-valid-rule="notNull&isFloat"/>&nbsp;元
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">
              <span class="requires">*</span>餐段
            </label>
            <div class="col-sm-6 checkbox">
              <c:forEach var="mealPeriod" items="${mealPeriodList}">
                <label>
                  <input class="J_mealPeriod"  type="checkbox" value="${mealPeriod.id}" name="mealPeriodIdList">${mealPeriod.name}
                </label>
              </c:forEach>
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
                  <button class="btn-default btn" type="reset">
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
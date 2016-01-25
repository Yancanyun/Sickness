<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/25
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
      </li>
      <li>
        <a href="#">会员管理</a>
      </li>
      <li class="active">
        会员积分管理
      </li>
    </ol>
    <h2>会员积分管理</h2>
    <div class="alert alert-success col-sm-12
                            J_msg" role="alert">添加成功！</div>
    <!-- <div class="alert alert-danger col-sm-12 J_msg" role="alert">添加失败！</div> -->
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>${vipGrade.name}&nbsp;积分设置</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal">
          <div class="form-group">
            <label class="col-sm-3 control-label">是否启用积分</label>
            <div class="col-sm-3">
              <label class="radio-inline">
                <input type="radio" class="J_startRadio" name="status" value="1"> 是
              </label>
              <label class="radio-inline">
                <input type="radio" class="J_stopRadio" name="status"  value="0" checked> 否
              </label>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>积分规则</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_operForm" id="dataForm">
          <input type="hidden" class="gradeIdInp" name="gradeId" value="${vipGrade.id}">
          <div>
            <h4>完善信息</h4>
            <hr/>
            <div class="form-group">
              <label class="col-sm-3 control-label">赠送积分</label>
              <div class="col-sm-6">
                <input type="text" class="w180" data-valid-rule="isNumber | isNull" name="" value="" >
              </div>
            </div>
          </div>
          <div class="J_subContainer" type="0">
            <h4>消费时积分设置</h4>
            <hr/>
            <div class="form-group">
              <label class="col-sm-3 control-label">消费方式</label>
              <div class="col-sm-3">
                <select class="form-control w180">
                  <option value="1" selected="selected" name="zhifubao">支付宝</option>
                  <option value="2" name="xianjin">现金</option>
                  <option value="3" name="yinhangka">银行卡</option>
                </select>
              </div>
              <button class="btn-success btn J_addBtn" type="button"><i class="fa fa-plus"></i>&nbsp;添加</button>
            </div>
            <div class="table-responsive">
              <table class="col-sm-9 table table-hover table-bordered">
                <thead>
                <tr>
                  <th class="w20">消费方式</th>
                  <th>积分规则</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody id="J_template">
                <tr data-type-num ="1"data-pointPlan-id="1">
                  <td>支付宝</td>
                  <td>
                    <input type="text" class="w180 h20" data-valid-rule="isNumber & notNull" name="" value="">&nbsp;&nbsp;元&nbsp;&nbsp;=&nbsp;&nbsp;1积分
                  </td>
                  <td>
                    <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                  </td>
                </tr>
                <tr data-type-num = "2"data-pointPlan-id="2">
                  <td>现金</td>
                  <td>
                    <input type="text" class="w180 h20" data-valid-rule="isNumber & notNull" name="" value="">&nbsp;&nbsp;元&nbsp;&nbsp;=&nbsp;&nbsp;1积分
                  </td>
                  <td>
                    <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="J_subContainer" type="1">
            <h4>储值时积分设置</h4>
            <hr/>
            <div class="form-group">
              <label class="col-sm-3 control-label">储值方式</label>
              <div class="col-sm-3">
                <select class="form-control w180"  name="">
                  <option value="1" selected="selected">支付宝</option>
                  <option value="2">现金</option>
                  <option value="3">银行卡</option>
                </select>
              </div>
              <button class="btn-success btn J_addBtn" type="button"><i class="fa fa-plus"></i>&nbsp;添加</button>
            </div>
            <div class="table-responsive">
              <table class="col-sm-9 table table-hover table-bordered">
                <thead>
                <tr>
                  <th class="w20">储值方式</th>
                  <th>积分规则</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody id="J_template">
                </tbody>
              </table>
            </div>
          </div>
          <div>
            <h4>积分兑换设置</h4>
            <hr/>
            <div class="col-sm-2 col-sm-offset-3">
              <input type="text" class="form-control w180" data-valid-rule="isNumber|isNull" name="0" value="" form="dataForm">
            </div>
            <label class="col-sm-1 text-left control-label">分 = 1元</label>
          </div>
        </form>
      </div>
      <div class="panel-footer">
        <div class="row">
          <div class="col-sm-6 col-sm-offset-3">
            <div class="btn-toolbar">
              <button data-btn-loading-text="正在保存，请稍后" data-btn-type="loading" type="submit" class="btn-primary btn J_submitBtn">
                <i class="fa fa-save"></i>
                &nbsp;保存
              </button>
              <button type="reset" class="btn-default btn"><i class="fa fa-undo"></i>&nbsp;重置</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%--


当前等级为：${vipGrade.name}<br>
<c:forEach items="${vipIntegralDtoList}" var="vipIntegralDto">
  <c:if test="${vipIntegralDto.type == 0}">
    ${vipIntegralDto.integralType}:${vipIntegralDto.value}<br>
  </c:if>
</c:forEach>
*******************************************************************<br>
<c:forEach items="${vipIntegralDtoList}" var="vipIntegralDto">
  <c:if test="${vipIntegralDto.type != 0}">
    ${vipIntegralDto.integralType}:${vipIntegralDto.value}<br>
  </c:if>
</c:forEach>--%>

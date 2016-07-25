<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/25
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a>
      </li>
      <li>
        <a href="#">会员管理</a>
      </li>
      <li class="active">
        会员积分管理
      </li>
    </ol>
    <h2>会员积分管理</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-success col-sm-12 J_msg" role="alert">${msg}</div>
    </c:if>
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
              <c:if test="${vipGrade.integralEnableState == 1}">
              <label class="radio-inline">
                  <input type="radio" class="J_startRadio" name="status" value="1" checked> 是
              </label>
              <label class="radio-inline">
                <input type="radio" class="J_stopRadio" name="status"  value="0"> 否
              </label>
              </c:if>
              <c:if test="${vipGrade.integralEnableState == 0}">
                <label class="radio-inline">
                  <input type="radio" class="J_startRadio" name="status" value="1"> 是
                </label>
                <label class="radio-inline">
                  <input type="radio" class="J_stopRadio" name="status"  value="0" checked> 否
                </label>
              </c:if>
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
        <form class="form-horizontal J_operForm operForm" id="dataForm" action="${website}admin/vip/integral/plan/new" method="post">
          <input type="hidden" class="gradeIdInp" name="gradeId" value="${vipGrade.id}">
          <div>
            <h4>完善信息</h4>
            <hr/>
            <div class="form-group">
              <label class="col-sm-3 control-label">赠送积分</label>
              <c:if test="${!empty vipIntegralDtoList}">
                <c:forEach items="${vipIntegralDtoList}" var="vipIntegralDto">
                  <c:if test="${vipIntegralDto.type == 0}">
                    <div class="col-sm-6">
                      <input type="text" class="w180" data-valid-rule="isFloat | isNull" name="completeInfoIntegral" value="${vipIntegralDto.value}" >
                    </div>
                  </c:if>
                </c:forEach>
              </c:if>
              <c:if test="${empty vipIntegralDtoList}">
                <div class="col-sm-6">
                  <input type="text" class="w180" data-valid-rule="isFloat | isNull" name="completeInfoIntegral" value="" >
                </div>
              </c:if>
            </div>
          </div>
          <div class="J_subContainer" type="0">
            <h4>消费时积分设置</h4>
            <hr/>
            <div class="form-group">
              <label class="col-sm-3 control-label">消费方式</label>
              <div class="col-sm-2">
                <select class="form-control w180">
                  <option value="2" selected="selected" name="conCashToIntegral">现金消费</option>
                  <option value="3" name="conCardToIntegral">刷卡消费</option>
                  <option value="4" name="conOnlineToIntegral">在线支付</option>
                </select>
              </div>
              <button class="btn-success btn J_addBtn" type="button"><i class="fa fa-plus"></i>&nbsp;添加</button>
            </div>
            <div class="table-responsive">
              <table class="table-list col-sm-9 table table-hover table-bordered">
                <thead>
                <tr>
                  <th >消费方式</th>
                  <th>积分规则</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody id="J_template">
                <c:forEach items="${vipIntegralDtoList}" var="vipIntegralDto">
                    <c:if test="${vipIntegralDto.type >= 2 && vipIntegralDto.type <= 4}">
                      <tr data-type-num ="${vipIntegralDto.type}" data-pointPlan-id="${vipIntegralDto.id}">
                        <td>${vipIntegralDto.integralType}</td>
                        <td>
                          <input type="text" data-valid-rule="isFloat & notNull" name="${vipIntegralDto.integralTypeName}" value="${vipIntegralDto.value}">&nbsp;&nbsp;元&nbsp;&nbsp;=&nbsp;&nbsp;1积分
                        </td>
                        <td>
                          <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                        </td>
                      </tr>
                    </c:if>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <div class="J_subContainer" type="1">
            <h4>储值时积分设置</h4>
            <hr/>
            <div class="form-group">
              <label class="col-sm-3 control-label">储值方式</label>
              <div class="col-sm-2">
                <select class="form-control w180"  name="">
                  <option value="5" selected="selected" name="recCashToIntegral">现金储值</option>
                  <option value="6" name="recCardToIntegral">刷卡储值</option>
                  <option value="7" name="recOnlineToIntegral">在线支付储值</option>
                </select>
              </div>
              <button class="btn-success btn J_addBtn" type="button"><i class="fa fa-plus"></i>&nbsp;添加</button>
            </div>
            <div class="table-responsive">
              <table class="table-list col-sm-9 table table-hover table-bordered">
                <thead>
                <tr>
                  <th>储值方式</th>
                  <th>积分规则</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody id="J_template">
                <c:forEach items="${vipIntegralDtoList}" var="vipIntegralDto">
                  <c:if test="${vipIntegralDto.type >= 5 && vipIntegralDto.type <= 7}">
                    <tr data-type-num ="${vipIntegralDto.type}" data-pointPlan-id="${vipIntegralDto.id}">
                      <td>${vipIntegralDto.integralType}</td>
                      <td>
                        <input type="text" data-valid-rule="isFloat & notNull" name="${vipIntegralDto.integralTypeName}" value="${vipIntegralDto.value}">&nbsp;&nbsp;元&nbsp;&nbsp;=&nbsp;&nbsp;1积分
                      </td>
                      <td>
                        <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                      </td>
                    </tr>
                  </c:if>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <div>
            <h4>积分兑换设置</h4>
            <hr/>
            <div class="col-sm-2 col-sm-offset-3">
              <c:set var="flag" value=""/>
              <c:forEach items="${vipIntegralDtoList}" var="vipIntegralDto">
                <c:if test="${vipIntegralDto.type == 7}">
                  <c:set var="flag" value="${vipIntegralDto.value}"/>
                </c:if>
              </c:forEach>
              <input type="text" class="form-control w180" data-valid-rule="isFloat|isNull" name="integralToMoney" value="${flag}" form="dataForm">
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
              <button type="reset" class="btn-default btn J_resetBtn"><i class="fa fa-undo"></i>&nbsp;重置</button>
              <button type="button" class="btn-default btn" onclick="history.go(-1)"><i class="fa fa-reply"></i>&nbsp;返回</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        会员等级管理
      </li>
    </ol>
    <h2>会员等级管理</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-success col-sm-12
                            J_msg" role="alert">${msg}</div>
    </c:if>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>会员等级</h4>
      </div>
      <div class="panel-body">
        <a href="${website}admin/vip/grade/new" class="btn btn-success margin-bottom-15"><i class="fa fa-plus"></i>&nbsp;添加会员等级</a>
        <div class="table-responsive">
          <table class="table table-hover table-bordered">
            <thead>
            <tr>
              <th>会员等级名称</th>
              <th>会员价方案</th>
              <th>最低消费金额</th>
              <th>信用额度</th>
              <th>结算周期</th>
              <th>升级预提醒额度</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody id="J_template">
            <c:forEach var="vipGradeDto" items="${vipGradeDtoList}">
            <tr data-grade-id="${vipGradeDto.vipGrade.id}">
              <td>${vipGradeDto.vipGrade.name}</td>
              <td>${vipGradeDto.vipDishPricePlanName}</td>
              <td>${vipGradeDto.vipGrade.minConsumption}</td>
              <td>${vipGradeDto.vipGrade.creditAmount}</td>
              <td class="J_vipDishPrice">${vipGradeDto.vipGrade.settlementCycle}</td>
              <td>${vipGradeDto.vipGrade.preReminderAmount}</td>
              <td class="hidden J_cardPolicy">${vipGradeDto.vipGrade.cardPolicy}</td>
              <td><a href="${website}admin/vip/grade/update/${vipGradeDto.vipGrade.id}" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                <a href="javascript:;" class="label-info J_delete"><i class="fa fa-times"></i>&nbsp;删除</a>
                <a href="javascript:;" class="label-info J_detail"><i class="fa fa-list"></i>&nbsp;查看卡片政策</a>
                <a href="${website}admin/vip/integral/plan/list/${vipGradeDto.vipGrade.id}" class="label-info"><i class="fa fa-cog"></i>&nbsp;积分管理</a>
              </td>
            </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
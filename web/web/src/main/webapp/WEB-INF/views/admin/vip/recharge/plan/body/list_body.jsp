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
      <li class="active">充值方案管理
      </li>
    </ol>
    <h2>充值方案管理</h2>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>充值方案管理</h4>
      </div>
      <div class="panel-body">
        <a href="javascript:;" class="btn btn-success margin-bottom-15 J_add"><i class="fa fa-plus"></i>&nbsp;添加充值方案管理</a>
        <form class="J_operForm" method="" action="">
          <input class="J_hidden" type="hidden" name="id" value=""/>
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>方案名称</th>
                <th>实付金额</th>
                <th>充值金额</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody id="J_template">
              <c:forEach var="vipRechargePlan" items="${vipRechargePlanList}">
              <tr recharge-plan-id="${vipRechargePlan.id}">
                <td class="J_name">${vipRechargePlan.name}</td>
                <td class="J_payAmount">${vipRechargePlan.payAmount}</td>
                <td class="J_rechargeAmount">${vipRechargePlan.rechargeAmount}</td>
                <td class="J_status">${vipRechargePlan.statusStr}</td>
                <td>
                  <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                  <c:if test="${vipRechargePlan.status == 1}">
                  <a class="label-info J_change" href="javascript:;"><i class="fa fa-circle"></i>&nbsp;停用</a>
                  </c:if>
                  <c:if test="${vipRechargePlan.status == 0}">
                  <a class="label-info J_change" href="javascript:;"><i class="fa fa-check"></i>&nbsp;启用</a>
                  </c:if>
                <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                </td>
              </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">会员管理</a></li>
      <li class="active">多倍积分方案管理</li>
    </ol>
    <h2>多倍积分方案管理</h2>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>列表</h4>
      </div>
      <div class="panel-body">
        <a href="javascript:;" class="btn btn-success margin-bottom-15 J_add"><i class="fa fa-plus"></i>&nbsp;添加方案</a>
        <div class="table-responsive">
          <form class="J_operForm" action="" method="">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>积分类型名称</th>
                <th>积分倍数</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="plan" items="${planList}">
                <tr data-plan-id="${plan.id}" data-status="${plan.status}">
                  <td>${plan.name}</td>
                  <td>${plan.integralMultiples}</td>
                  <td>${plan.startTimeString}</td>
                  <td>${plan.endTimeString}</td>
                  <td><c:if test="${plan.status == 1}">启用</c:if>
                      <c:if test="${plan.status == 0}">停用</c:if>
                  </td>
                  <td>
                    <c:if test="${plan.status == 0}">
                      <a href="javascript:;" class="label-info J_change"><i class="fa fa-check"></i>&nbsp;启用</a>
                    </c:if>
                    <c:if test="${plan.status == 1}">
                      <a href="javascript:;" class="label-info J_change"><i class="fa fa-circle"></i>&nbsp;停用</a>
                    </c:if>
                    <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                    <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
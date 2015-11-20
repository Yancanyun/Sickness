<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
      </li>
      <li>
        <a href="#">饭店管理</a>
      </li>
      <li>
        <a href="#" class="active">餐段管理</a>
      </li>
    </ol>
    <h2>餐段管理</h2>
    <c:if test="${!empty msg}">
      <c:choose>
        <c:when test="${msg eq '添加成功!'}">
          <div class="alert alert-success col-sm-12 J_msg" role="alert">${msg}</div>
        </c:when>
        <c:otherwise>
          <div class="alert alert-danger col-sm-12 J_msg" role="alert">${msg}</div>
        </c:otherwise>
      </c:choose>
    </c:if>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>当前餐段设置</h4>
      </div>
      <div class="panel-body">
        <div class="form-group">
          <label class="col-sm-3 control-label">当前餐段</label>
          <div class="col-sm-3">
            <select class="form-control J_period" name="">
              <c:forEach var="mealPeriod" items="${mealPeriodList}">
                <c:if test="${mealPeriod.status eq 1}">
                  <c:choose>
                    <c:when test="${mealPeriod.id eq currentMealPeriod.id}">
                      <option value="${currentMealPeriod.id}" selected="selected">${currentMealPeriod.name}</option>
                    </c:when>
                    <c:otherwise>
                      <option value="${mealPeriod.id}">${mealPeriod.name}</option>
                    </c:otherwise>
                  </c:choose>
                </c:if>
              </c:forEach>
            </select>
          </div>
          <button class="btn-primary btn J_set" type="button"><i class="fa fa-save"></i>&nbsp;保存</button>
        </div>
      </div>
    </div>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>餐段管理</h4>
      </div>
      <div class="panel-body">
        <a href="javascript:;" class="btn btn-success margin-bottom-15 J_add"><i class="fa fa-plus"></i>&nbsp;添加餐段</a>
        <form class="J_operForm" method="post" action="${website}admin/restaurant/meal/period">
          <input class="J_hidden" type="hidden" name="id" value=""/>
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>名称</th>
                <th>状态</th>
                <th>排序</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody id="J_template">
              <c:forEach var="mealPeriod" items="${mealPeriodList}">
                <tr data-period-id="${mealPeriod.id}">
                  <td class="J_name">${mealPeriod.name}</td>
                  <c:choose>
                    <c:when test="${mealPeriod.status eq 1}">
                      <td class="J_status">启用</td>
                    </c:when>
                    <c:otherwise>
                      <td class="J_status">停用</td>
                    </c:otherwise>
                  </c:choose>
                  <td class="J_weight">${mealPeriod.weight}</td>
                  <td>
                    <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                    <c:choose>
                      <c:when test="${mealPeriod.status eq 1}">
                        <a href="javascript:;" class="label-info J_change"><i class="fa fa-circle"></i>&nbsp;停用</a>
                      </c:when>
                      <c:otherwise>
                        <a href="javascript:;" class="label-info J_change"><i class="fa fa-check"></i>&nbsp;启用</a>
                      </c:otherwise>
                    </c:choose>
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

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/27
  Time: 15:35
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
        <a href="${website}admin/party/group/vip">会员管理</a>
      </li>
      <li>
        <a href="${website}admin/vip/price/plan/list" class="active">会员价管理</a>
      </li>
    </ol>
    <h2>会员价方案管理</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-success col-sm-12 J_msg" role="alert">${msg}</div>
    </c:if>
    <!-- <div class="alert alert-danger col-sm-12 J_msg" role="alert">添加失败！</div> -->
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>会员价方案</h4>
      </div>
      <div class="panel-body">
        <a href="javascript:;" class="btn btn-success margin-bottom-15 J_add"><i class="fa fa-plus"></i>&nbsp;添加会员价方案</a>
        <form class="J_operForm" action="${website}admin/vip/price/plan/new"  method="post" >
          <input class="J_hidden" type="hidden" name="id" value=""/>
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>方案名称</th>
                <th>方案描述</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody id="J_template">
              <c:forEach items="${vipDishPricePlanList}" var="vipDishPricePlan" >
              <tr data-plan-id="${vipDishPricePlan.id}">
                <td class="J_name">${vipDishPricePlan.name}</td>
                <td class="J_desc">${vipDishPricePlan.description}</td>
                <td>
                  <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                  <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                  <a href="javascript:;" src="${website}admin/vip/price/list/${vipDishPricePlan.id}" class="label-info J_detail"><i class="fa fa-list"></i>&nbsp;查看详情</a>
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
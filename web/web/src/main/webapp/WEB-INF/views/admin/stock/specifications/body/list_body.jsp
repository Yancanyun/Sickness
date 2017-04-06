<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 17/3/8
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
  <div class="row">
    <div class="col-sm-12">
      <ol class="breadcrumb">
        <li>
          <a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a>
        </li>
        <li>
          <a href="${website}admin/specifications">库存管理</a>
        </li>
        <li class="active">规格管理</li>
      </ol>
      <h2>规格管理</h2>
    </div>
    <div class="col-sm-12">
      <c:if test="${!empty msg}">
        <div class="alert alert-success J_tip" role="alert">${msg}</div>
      </c:if>
      <div class="panel panel-info">
        <div class="panel-heading">
          <h4>规格列表</h4>
        </div>
        <div class="panel-body">
          <a class="btn btn-success margin-bottom-15 J_addBtn" href="${website}admin/stock/specifications/tonew">
            <i class="fa fa-plus"></i>&nbsp;添加规格
          </a>
          <form class="J_operForm">
            <input type = "hidden" class="J_id" name="id" value=""/>
            <div class="table-responsive">
              <table class="table table-hover table-bordered">
                <thead>
                <tr>
                  <th class="col-sm-1">订货单位</th>
                  <th class="col-sm-1">订货单位到库存的转换关系</th>
                  <th class="col-sm-1">库存单位</th>
                  <th class="col-sm-1">库存单位到成本卡的转换关系</th>
                  <th class="col-sm-1">成本卡单位</th>
                  <th class="col-sm-1">操作</th>
                </tr>
                </thead>
                <tbody id="J_template"></tbody>
              </table>
              <div class="J_pagination"></div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div><!--row-->
</div><!--container-->

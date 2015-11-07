<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">基本信息管理</a></li>
      <li class="active">搜索风向标</li>
    </ol>
    <h2>搜索风向标</h2>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>顾客点餐平台</h4>
      </div>
      <div class="panel-body clearfix">
        <form class="form-horizontal J_addForm0" action="" method="" data-type="0">
          <div class="form-group static-height">
            <div class="col-sm-3">
              <input type="text" class="form-control J_input-key" data-valid-tip="请输入1~15个关键字|关键字有误，请重新输入" data-valid-rule="length(0,16)" name="key" value="">
            </div>
            <button class="btn btn-success J_add" type="submit"><i class="fa fa-plus"></i>&nbsp;添加</button>
          </div>
        </form>
        <div class="tags J_tags">
          <c:forEach var="orderingKeywords" items="${orderingKeywordsList}">
            <label class="label-info" data-order-id="${orderingKeywords.id}">
              <span>${orderingKeywords.key}</span>
              <i class="fa fa-times J_del"></i>
            </label>
          </c:forEach>
        </div>
      </div>
    </div>
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>服务员系统</h4>
      </div>
      <div class="panel-body clearfix">
        <form class="form-horizontal J_addForm1" action="" method="" data-type="1">
          <div class="form-group static-height">
            <div class="col-sm-3">
              <input type="text" class="form-control J_input-key" data-valid-tip="请输入1~15个关键字|关键字有误，请重新输入" data-valid-rule="length(0,16)" name="key" value="">
            </div>
            <button class="btn btn-success J_add" type="submit"><i class="fa fa-plus"></i>&nbsp;添加</button>
          </div>
        </form>
        <div class="tags J_tags">
          <c:forEach var="waiterSystemKeyword" items="${waiterSystemKeywordsList}">
            <label class="label-info" data-order-id="${waiterSystemKeyword.id}">
              <span>${waiterSystemKeyword.key}</span>
              <i class="fa fa-times J_del"></i>
            </label>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>
</div>

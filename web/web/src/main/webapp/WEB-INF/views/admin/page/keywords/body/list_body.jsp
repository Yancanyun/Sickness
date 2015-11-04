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
        <form class="form-horizontal J_addForm1" action="" method="" data-type="0">
          <div class="form-group static-height">
            <div class="col-sm-3">
              <input type="text" class="form-control J_input-key" data-valid-tip="请输入关键字|关键字不能为空" data-valid-rule="length(0,15)" name="key" value="">
            </div>
            <a href="javascript:;" class="btn btn-success J_add"><i class="fa fa-plus"></i>&nbsp;添加</a>
          </div>
        </form>
        <div class="tags J_tags">
          <%--<c:forEach var="orderingKeywords" items="orderingKeywordsList"--%>
        </div>
      </div>
    </div>
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>服务员系统</h4>
      </div>
      <div class="panel-body clearfix">
        <form class="form-horizontal J_addForm2" action="" method="" data-type="1">
          <div class="form-group static-height">
            <div class="col-sm-3">
              <input type="text" class="form-control J_input-key" data-valid-tip="请输入关键字|关键字不能为空" data-valid-rule="length(0,15)" name="key" value="">
            </div>
            <a href="javascript:;" class="btn btn-success J_add"><i class="fa fa-plus"></i>&nbsp;添加</a>
          </div>
        </form>
        <div class="tags J_tags">
        </div>
      </div>
    </div>
  </div>
</div>

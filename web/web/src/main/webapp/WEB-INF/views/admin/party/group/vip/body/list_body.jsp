<%--
  User: chenyuting
  Time: 2015/11/3 09:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li class="active">会员管理</li>
    </ol>
    <h2>会员管理-会员列表</h2>
  </div>
  <div class="col-sm-12">
    <c:if test="${!empty msg}">
      <div class="alert alert-success J_tip" role="alert">${msg}</div>
    </c:if>
    <!-- 查询表单 start-->
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>搜索</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_searchForm">
          <div class="form-group">
            <label class="col-sm-3 control-label">关键字</label>
            <div class="col-sm-3">
              <input type="text" class="col-sm-3 form-control J_key" placeholder="请输入关键字" name="keyword" value="">
            </div>
          </div>
          <div class="col-sm-6 col-sm-offset-3">
            <div class="btn-toolbar">
              <button class="btn btn-primary J_search" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
          </div>
        </form>
      </div>
    </div>
    <!-- 查询表单 end-->
    <!-- 查询结果 start-->
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>会员列表</h4>
      </div>
      <div class="panel-body">
        <a class="btn btn-success margin-bottom-15" href="${website}admin/party/group/vip/new"><i class="fa fa-plus"></i>&nbsp;添加会员</a>
        <div class="table-responsive">
          <table class="table table-hover table-bordered">
            <thead>
            <tr>
              <th>姓名</th>
              <th>电话</th>
              <th>卡号</th>
              <th>余额</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody id="J_template">
            </tbody>
          </table>
        </div>
        <div class="J_pagination"></div>
      </div>
    </div>
    <!-- 查询结果 end-->
  </div>
</div>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li class="active">会员管理</li>
    </ol>
    <h2>会员管理-消费详情列表</h2>
  </div>
  <div class="col-sm-12">
    <!-- 查询表单 start-->
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>搜索</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_searchForm" action="" method="">
          <input class="J_partyId" type="hidden" name="partyId" value="${partyId}">
          <div class="form-group">
            <label class="col-sm-3 control-label">开始时间：</label>
            <div class="col-sm-2">
              <input class="w180 date" readonly name="startTime" value="">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">结束时间：</label>
            <div class="col-sm-2">
              <input class="w180 date" readonly name="endTime" value="">
            </div>
          </div>
          <div class="col-sm-6 col-sm-offset-3">
            <div class="btn-toolbar">
              <button class="btn btn-primary" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
          </div>
        </form>
      </div>
    </div>
    <!-- 查询表单 end-->
    <!-- 查询结果 start-->
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>消费详情列表</h4>
      </div>
      <div class="panel-body">
        <div class="table-responsive">
          <table class="table table-hover table-bordered">
            <thead>
            <tr>
              <th>消费时间</th>
              <th>消费类型</th>
              <th>原有金额</th>
              <th>消费（充值）金额</th>
              <th>实付金额</th>
              <th>消费后金额</th>
              <th>操作人</th>
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
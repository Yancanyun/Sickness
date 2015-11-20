<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">基本信息管理</a></li>
      <li class="active">打印机管理</li>
    </ol>
    <h2>打印机管理</h2>
  </div>
  <div class="col-sm-12">
    <c:if test="${!empty msg}">
      <div class="alert alert-success J_tip" role="alert">${msg}</div>
    </c:if>
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>打印机列表</h4>
      </div>
      <div class="panel-body clearfix">
        <a href="#" class="btn btn-success"><i class="fa fa-plus"></i>&nbsp;添加打印机</a>
        <div class="margin-top-20">
          <div class="printer-classify" id="1">
            <span>吧台打印机</span>
            <span>192.168.1.21</span>
            <em>1</em>
            <div class="text-right">
              <a href="#" class="padding-right-10"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
              <a href="javascript:;" class="J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            </div>
          </div>
          <div class="printer-classify" id="2">
            <span>吧台打印机</span>
            <span>192.168.1.22</span>
            <em>2</em>
            <div class="text-right">
              <a href="#" class="padding-right-10"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
              <a href="javascript:;" class="J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            </div>
          </div>
          <div class="printer-classify" id="3">
            <span>吧台打印机</span>
            <span>192.168.1.23</span>
            <em>3</em>
            <div class="text-right">
              <a href="#" class="padding-right-10"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
              <a href="javascript:;" class="J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

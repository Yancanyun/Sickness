<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sanqi
  Date: 2017/3/21
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
                <li><a href="${website}admin/stock">库存管理</a></li>
                <li>
                    <a href="${website}admin/stock/kitchenItem" class="active">存放点物品列表</a>
                </li>
            </ol>
            <h2>存放点物品列表</h2>
        </div>
        <div class="col-sm-12">
            <div class="alert alert-success J_tip" role="alert">保存成功！</div>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>存放点物品列表</h4>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>物品编号</th>
                                <th>名称</th>
                                <th>助记码</th>
                                <th>规格</th>
                                <th>库存量</th>
                                <th>状态</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="J_template">
                            </tbody>
                        </table>
                        <div class="J_pagination"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
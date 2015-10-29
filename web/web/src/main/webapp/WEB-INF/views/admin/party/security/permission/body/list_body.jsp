<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li>
                <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
            </li>
            <li>
                <a href="#">权限管理</a>
            </li>
            <li class="active">权限配置</li>
        </ol>
        <h2>权限配置</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>权限列表</h4>
            </div>
            <div class="panel-body">
                <a class="btn btn-success margin-bottom-15 J_addBtn" href="#"><i class="fa fa-plus"></i>&nbsp;添加权限</a>

                <form class="J_operForm">
                    <input type="hidden" class="J_id" name="id" value=""/>

                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>表达式</th>
                                <th>描述</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="J_template">

                            </tbody>
                        </table>
                        <div class="J_pagination"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!--row-->
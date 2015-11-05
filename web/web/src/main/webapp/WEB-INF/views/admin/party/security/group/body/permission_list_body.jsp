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
            <li class="active">安全组管理</li>
        </ol>
        <h2>安全组配置</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>安全组名称：${securityGroup.name}</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_addForm" action="${website}admin/party/security/group/permission" method="post">
                    <div class="form-group">
                        <input type="hidden" value="${securityGroup.id}" name="groupId">

                        <div class="col-sm-3">
                            <select class="form-control J_select" name="permissionId">
                                <option value="-1">请选择</option>
                                <c:forEach var="permission" items="${noSelectedPermissionList}">
                                    <option value="${permission.id}">${permission.expression}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button class="btn btn-success J_add" type="submit"><i class="fa fa-plus"></i>&nbsp;添加权限
                        </button>
                    </div>
                </form>
                <form class="J_operForm">
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">存放点管理</li>
        </ol>
        <h2>存放点管理-存放点列表</h2>
    </div>
    <div class="col-sm-12">
        <c:if test="${!empty msg}">
            <div class="alert alert-success col-sm-12 J_msg" role="alert"> ${msg}</div>
        </c:if>

        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>存放点列表</h4>
            </div>
            <div class="panel-body">
                <a href="${website}admin/storage/depot/tonew" class="btn btn-success margin-bottom-15"><i class="fa fa-plus"></i>&nbsp;添加存放点</a>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>简介</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${depotList}" var="storageDepot">
                            <tr data-depot-id="${storageDepot.id}">
                                <td>${storageDepot.name}</td>
                                <td>${storageDepot.introduction}</td>
                                <td>
                                    <a href="${website}admin/storage/depot/toupdate/${storageDepot.id}" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                                    <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
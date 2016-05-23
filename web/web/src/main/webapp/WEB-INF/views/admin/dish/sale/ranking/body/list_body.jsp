<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${webiste}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">菜品管理</a></li>
            <li class="active">销量排行</li>
        </ol>
        <h2>菜品管理-销量排行</h2>
    </div>
    <div class="col-sm-12">
        <c:if test="${!empty msg}">
            <div class="alert alert-success J_tip" role="alert">${msg}</div>
        </c:if>
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>列表</h4>
            </div>
            <div class="panel-body">
                <a href="${website}admin/dish/sale/ranking/new" class="btn btn-success margin-bottom-15"><i class="fa fa-plus"></i>&nbsp;添加菜品</a>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <%--<th>菜品总分类</th>--%>
                            <%--<th>菜品小类</th>--%>
                            <th>编号</th>
                            <th>助记码</th>
                            <th>菜品名称</th>
                            <th>单位</th>
                            <th>定价</th>
                            <th>折扣</th>
                            <th>售价</th>
                            <th>状态</th>
                            <%--<th>上月销量</th>--%>
                            <%--<th>总销量</th>--%>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="dishTagDto" items="${dishTagDtoList}">
                            <tr data-dish-id="${dishTagDto.id}">
                                <%--<td>${dishTagDto.categoryNameStr}</td>--%>
                                    <%--<td>${dishTagDto.tagNameStr}</td>--%>
                                <td>${dishTagDto.dishNumber}</td>
                                <td>${dishTagDto.dishAssistantCode}</td>
                                <td>${dishTagDto.dishName}</td>
                                <td>${dishTagDto.dishUnitName}</td>
                                <td>${dishTagDto.dishPrice}</td>
                                <td>${dishTagDto.dishDiscount}</td>
                                <td>${dishTagDto.dishSalePrice}</td>
                                <td>${dishTagDto.dishStatusStr}</td>
                                <td>
                                    <a href="javascript:;" class="label-info J_revoke"><i class="fa fa-undo"></i>&nbsp;撤销</a>
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
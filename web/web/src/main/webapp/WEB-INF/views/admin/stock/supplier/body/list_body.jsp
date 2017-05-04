<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">供货商管理</li>
        </ol>
        <h2>库存管理-供货商管理</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>供货商列表</h4>
            </div>
            <div class="panel-body">
                <a class="btn btn-success margin-bottom-15 J_add" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加供货商</a>

                <form class="J_operForm">
                    <input class="J_name" type="hidden" value="" name="id"/>
                    <input class="J_name" type="hidden" value="" name="partyId"/>
                    <input class="J_name" type="hidden" value="" name="name"/>
                    <input class="J_name" type="hidden" value="" name="shortName"/>
                    <input class="J_name" type="hidden" value="" name="contactPerson"/>
                    <input class="J_name" type="hidden" value="" name="contactPhone"/>
                    <input class="J_name" type="hidden" value="" name="address"/>
                    <input class="J_name" type="hidden" value="" name="email"/>
                    <input class="J_name" type="hidden" value="" name="website"/>
                    <input class="J_name" type="hidden" value="" name="description"/>

                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>简称</th>
                                <th>联系人</th>
                                <th>联系电话</th>
                                <th>联系地址</th>
                                <th>电子邮箱</th>
                                <th>网址</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="J_template">
                                <c:forEach var="supplier" items="${supplierList}">
                                    <tr data-supplier-id="${supplier.id}" data-party-id="${supplier.partyId}">
                                        <td>${supplier.name}</td>
                                        <td>${supplier.shortName}</td>
                                        <td>${supplier.contactPerson}</td>
                                        <td>${supplier.contactPhone}</td>
                                        <td>${supplier.address}</td>
                                        <td>${supplier.email}</td>
                                        <td>${supplier.website}</td>
                                        <td class="hidden description">${supplier.description}</td>
                                        <td>
                                            <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                                            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
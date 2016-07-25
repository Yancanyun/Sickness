<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">结算中心管理</li>
        </ol>
        <h2>结算中心管理</h2>
    </div>
    <div class="col-sm-12">
        <c:if test="${eMsg!=null}">
        <div class="alert alert-danger J_tip" role="alert">${eMsg}</div>
        </c:if>
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_searchForm" action="" method="">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">开始时间：</label>
                        <div class="col-sm-2">
                            <input type="text" class="w190 form-control date" readonly name="startDate" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">结束时间：</label>
                        <div class="col-sm-2">
                            <input type="text" class="w190 form-control date" readonly name="endDate" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">供货商：</label>
                        <div class="col-sm-2">
                            <select class="w190 form-control" name="supplier" value="">
                                <option value="0">请选择</option>
                                <c:forEach items="${supplierList}" var="supplier">
                                    <option value="${supplier.id}">${supplier.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-3">
                        <div class="btn-toolbar">
                            <button class="btn btn-primary J_searchBtn" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>列表</h4>
            </div>
            <div class="panel-body">
                <div class="text-right">
                    <a href="${website}admin/storage/settlement/supplier/export" class="btn btn-warning margin-bottom-15 J_exportExcel"><i class="fa fa-download"></i>&nbsp;导出Excel</a>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th>供货商</th>
                            <th>名称</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th>经手人</th>
                            <th>操作人</th>
                            <th>总金额</th>
                        </tr>
                        </thead>
                        <tbody id="dataRender">
                        <c:forEach items="${supplierDtoList}" var="supplierDto">
                            <c:if test="${fn:length(supplierDto.storageItemDtoList)==0}">
                                <tr class="J_supplier">
                                    <td rowspan="" >${supplierDto.supplierName}</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td rowspan="">${supplierDto.totalMoney}</td>
                                </tr>
                            </c:if>
                            <c:if test="${fn:length(supplierDto.storageItemDtoList)==1}">
                                <tr class="J_supplier">
                                    <td rowspan="1">${supplierDto.supplierName}</td>
                                    <c:forEach items="${supplierDto.storageItemDtoList}" var="itemDto">
                                        <td>${itemDto.itemName}</td>
                                        <td>${itemDto.itemQuantity}</td>
                                        <td>${itemDto.itemMoney}</td>
                                        <td>${itemDto.handlerName}</td>
                                        <td>${itemDto.createdName}</td>
                                    </c:forEach>
                                    <td rowspan="1">${supplierDto.totalMoney}</td>
                                </tr>
                            </c:if>
                            <c:if test="${fn:length(supplierDto.storageItemDtoList)>1}">
                                <tr class="J_supplier">
                                    <td rowspan="${fn:length(supplierDto.storageItemDtoList)}">${supplierDto.supplierName}</td>
                                    <c:forEach items="${supplierDto.storageItemDtoList}" var="itemDto" end="0">
                                        <td>${itemDto.itemName}</td>
                                        <td>${itemDto.itemQuantity}</td>
                                        <td>${itemDto.itemMoney}</td>
                                        <td>${itemDto.handlerName}</td>
                                        <td>${itemDto.createdName}</td>
                                    </c:forEach>
                                    <td rowspan="${fn:length(supplierDto.storageItemDtoList)}">${supplierDto.totalMoney}</td>
                                </tr>
                                <c:forEach items="${supplierDto.storageItemDtoList}" var="itemDto" begin="1">
                                <tr>
                                    <td>${itemDto.itemName}</td>
                                    <td>${itemDto.itemQuantity}</td>
                                    <td>${itemDto.itemMoney}</td>
                                    <td>${itemDto.handlerName}</td>
                                    <td>${itemDto.createdName}</td>
                                </tr>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
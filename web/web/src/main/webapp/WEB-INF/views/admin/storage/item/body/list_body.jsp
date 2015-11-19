<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">库存物品管理</li>
        </ol>
        <h2>库存管理-库存物品管理</h2>
        <c:if test="${!empty msg}">
            <div class="alert alert-success col-sm-12 J_msg" role="alert"> ${msg}</div>
        </c:if>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_searchForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">搜索</label>

                        <div class="col-sm-3">
                            <input type="text" class="form-control w180" name="keyword" value=""
                                   placeholder="请输入名称/编号/助记码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">选择供货商</label>

                        <div class="col-sm-3">
                            <select class="form-control" name="supplierPartyId">
                                <option value="0">请选择</option>
                                <c:forEach var="supplier" items="${supplierList}">
                                    <option value="${supplier.partyId}">${supplier.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">库存分类</label>

                        <div class="col-sm-6">
                            <div class="checkbox block">
                                <label>
                                    <input class="J_selectAll" type="checkbox" value="0" name=""> 全部
                                </label>
                                <c:forEach var="tag" items="${tagList}">
                                    <label>
                                        <input class="J_storeType" type="checkbox" value="${tag.id}" name="tagIdList"> ${tag.name}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-3">
                        <div class="btn-toolbar">
                            <button class="btn-primary btn" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>库存物品列表</h4>
            </div>
            <div class="panel-body">
                <a class="btn btn-success margin-bottom-15" href="${website}admin/storage/item/new"><i class="fa fa-plus"></i>&nbsp;添加库存物品</a>
                <a class="btn btn-success margin-bottom-15" href="${website}admin/storage/item/unit/conversion/list"><i class="fa fa-exchange"></i>&nbsp;换算比例</a>

                <form class="J_operForm">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>助记码</th>
                                <th>名称</th>
                                <th>所属分类</th>
                                <th>供货商</th>
                                <th>最大库存量</th>
                                <th>最小库存量</th>
                                <th>出库方式</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="J_template"></tbody>
                        </table>
                        <div class="J_pagination"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

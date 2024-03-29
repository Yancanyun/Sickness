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
        <%--<div class="alert alert-success J_tip">提示信息!</div>--%>
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
                            <input type="text" class="form-control" name="keyword" value="" placeholder="请输入名称/编号/助记码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">选择供货商</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="supplierPartyId">
                                <option value="-1">请选择</option>
                                <c:forEach var="supplier" items="${supplierList}">
                                    <option value="${supplier.partyId}">${supplier.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">选择原配料</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="tagName">
                                <option value="-1">请选择</option>
                                <c:forEach var="ingredient" items="${ingredientList}">
                                    <option value="${ingredient.id}">${ingredient.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">库存分类</label>
                        <div class="col-sm-6">
                            <div class="checkbox block">
                                <label>
                                    <input class="J_selectAll"  type="checkbox" value="1" name="tagIdList"> 全部
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
                <a class="btn btn-success margin-bottom-15" href="${website}/admin/storage/item/tonew"><i class="fa fa-plus"></i>&nbsp;添加库存物品</a>
                <a href="${website}admin/storage/item/export" class="btn btn-warning pull-right J_export"><i class="fa fa-download"></i>&nbsp;导出Excel</a>
                <form class="J_operForm">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead class="v-center">
                            <tr>
                                <th rowspan="2">序号</th>
                                <th rowspan="2">名称</th>
                                <th rowspan="2">物品编号</th>
                                <th rowspan="2">助记码</th>
                                <th rowspan="2">原配料</th>
                                <th rowspan="2">所属分类</th>
                                <th rowspan="2">供货商</th>
                                <th class="width60" rowspan="2">入库总数量</th>
                                <th class="width60" rowspan="2">入库总金额</th>
                                <th rowspan="2">最新入库价格</th>
                                <th colspan="2">库存预警</th>
                                <th rowspan="2">出库方式</th>
                                <th rowspan="2">操作</th>
                            </tr>
                            <tr>
                                <th>上限</th>
                                <th>下限</th>
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

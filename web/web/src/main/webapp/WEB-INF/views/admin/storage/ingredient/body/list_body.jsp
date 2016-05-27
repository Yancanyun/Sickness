<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#"></a>库存管理</li>
            <li class="active">原配料管理</li>
        </ol>
        <h2>库存管理-原配料列表</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_searchForm" action="" method="">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">关键字</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" placeholder="请输入原配料姓名/编号/助记码" name="keyword" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">原配料分类</label>
                        <div class="col-sm-6">
                            <div class="checkbox block">
                                <label>
                                    <input type="checkbox" class="J_selectAll" name="tagIdList" value="0">
                                    全部
                                </label>
                                <c:forEach var="tag" items="${tagList}">
                                    <label>
                                        <input type="checkbox" class="J_ingredientType" name="tagIdList" value="${tag.id}">${tag.name}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-3">
                        <div class="toolbar">
                            <button class="btn btn-primary" type="submit"><i class="fa fa-search">&nbsp;搜索</i></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>原配料列表</h4>
            </div>
            <div class="panel-body">
                <a href="${website}admin/storage/ingredient/tonew" class="btn btn-success margin-bottom-15"><i class="fa fa-plus">&nbsp;添加原配料</i></a>
                <a href="1?" class="btn btn-warning pull-right J_export"><i class="fa fa-download">&nbsp;导出</i></a>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead class="v-center">
                        <tr>
                            <th rowspan="2">编号</th>
                            <th rowspan="2">名称</th>
                            <th rowspan="2">原配料编号</th>
                            <th rowspan="2">助记码</th>
                            <th rowspan="2">所属分类</th>
                            <th rowspan="2">订货单位</th>
                            <th rowspan="2" class="ratio-width">订货单位与库存换算关系</th>
                            <th rowspan="2">库存单位</th>
                            <th rowspan="2" class="ratio-width">库存单位与成本卡换算关系</th>
                            <th rowspan="2">成本卡单位</th>
                            <th colspan="2">库存预警</th>
                            <th colspan="3">结存</th>
                            <th rowspan="2">总数量</th>
                            <th rowspan="2">总金额</th>
                            <th rowspan="2">操作</th>
                        </tr>
                        <tr>
                            <th>上限</th>
                            <th>下限</th>
                            <th>均价</th>
                            <th>数量</th>
                            <th>金额</th>
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

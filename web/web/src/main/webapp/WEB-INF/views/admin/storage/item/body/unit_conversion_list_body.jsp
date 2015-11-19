
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li><a href="${website}admin/storage/item">库存物品管理</a></li>
            <li class="active">换算比例管理</li>
        </ol>
        <h2>库存物品管理-换算比例列表</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_searchForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">关键字</label>

                        <div class="col-sm-6">
                            <input class="w200" type="text" placeholder="请输入物品名称/助记码/物品编号" name="keyword" value="">
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
                <h4>换算比例列表</h4>
            </div>
            <div class="panel-body">
                <form class="J_operForm">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>物品名称</th>
                                <th>订货单位</th>
                                <th>订货单位与库存单位换算</th>
                                <th>库存单位</th>
                                <th>库存单位与成本卡单位换算</th>
                                <th>成本卡单位</th>
                                <th>计数单位</th>
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
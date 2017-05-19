<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
                <li><a href="${website}admin/stock"></a>库存管理</li>
                <li class="active">预警管理</li>
            </ol>
            <h2>库存管理-预警列表</h2>
        </div>
        <div class="col-sm-12">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>预警列表</h4>
                </div>
                <div class="panel-body">
                    <a href="#" class="btn btn-warning margin-bottom-15 J_export"><i class="fa fa-download">&nbsp;导出</i></a>
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead class="v-center">
                            <tr>
                                <th>厨房名称</th>
                                <th>物品ID</th>
                                <th>物品名称</th>
                                <th>预警内容</th>
                                <th>时间</th>
                                <th>操作</th>
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
</div>

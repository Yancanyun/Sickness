<%@ page contentType="text/html; charset=utf-8"%>
<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="${website}admin/stock">库存管理</a></li>
            <li>
                <a href="${website}admin/stock/kitchen/toList" class="active">存放点列表</a>
            </li>
        </ol>
        <h2>存放点列表</h2>
    </div>
    <div class="col-sm-12">
        <div class="alert alert-success J_tip" role="alert">保存成功！</div>
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>存放点列表</h4>
            </div>
            <div class="panel-body">
                <a href="${website}admin/stock/kitchen/add" class="btn btn-success margin-bottom-15"><i class="fa fa-plus"></i>&nbsp;添加存放点</a>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>负责人</th>
                            <th>简介</th>
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
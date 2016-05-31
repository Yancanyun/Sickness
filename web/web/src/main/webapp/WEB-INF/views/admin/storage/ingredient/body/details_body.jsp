<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">原配料管理</li>
        </ol>
        <h2>原配料管理-查看详情</h2>
    </div>
    <div class="col-sm-12">
        <form class="form-horizontal"  action="" method="">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>详情</h4>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            名称:
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.name}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            原配料编号
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.ingredientNumber}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            助记码
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.assistantCode}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            所属分类
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.tagName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            订货单位
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.orderUnitName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            订货单位与库存换算关系
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.orderToStorageRatio}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            库存单位
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.storageUnitName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            库存单位与成本卡换算关系
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.storageToCostCardRatio}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            成本卡单位
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.costCardUnitName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            库存预警上限
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.maxStorageQuantityStr}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            库存预警下限
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.minStorageQuantityStr}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            结存均价
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.averagePrice}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            结存数量
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.realQuantityStr}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            结存金额
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.realMoney}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            总数量
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.totalQuantityStr}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            总金额
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${ingredient.totalMoney}</p>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-3">
                        <div class="btn-toolbar">
                            <button class="btn-default btn" onclick="window.history.go(-1);" type="button"><i class="fa fa-undo"></i>&nbsp;返回上一页</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

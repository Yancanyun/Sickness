<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">库存管理查看详情</li>
        </ol>
        <h2>库存管理-查看详情</h2>
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
                            名称 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.name}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            助记码 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.assistantCode}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            物品编号 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.itemNumber}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            原配料 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.ingredientName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            分类 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.tagName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            供货商 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.supplierName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            订货单位 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.orderUnitName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            订货单位到库存单位转换比例 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.orderToStorageRatio}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            库存单位 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.storageUnitName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            库存单位到成本卡单位转换比例 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.storageToCostCardRatio}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            成本卡单位 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.costCardUnitName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            </span>计数单位 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.countUnitName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            库存预警上限 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.maxStorageQuantityStr}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            库存预警下限 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.minStorageQuantityStr}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            最新入库价格 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.lastStockInPrice}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            入库总数量 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.totalStockInQuantityStr}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            入库总金额 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.totalStockInMoney}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            出库方式 :
                        </label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${storageItem.stockOutTypeStr}</p>
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

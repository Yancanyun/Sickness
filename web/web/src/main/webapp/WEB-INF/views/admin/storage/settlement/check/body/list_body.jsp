<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li>
                <a href="${website}"><i class="fa fa-home"></i>&nbsp;首页</a>
            </li>
            <li>
                <a href="#">库存管理</a>
            </li>
            <li>
                <a href="${website}admin/storage/settlement/check" class="active">库存盘点</a>
            </li>
        </ol>
        <h2>库存盘点</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal operForm" autocomplete="off">
                    <div class="form-group col-sm-6"  >
                        <label class="col-sm-4 control-label">开始时间</label>
                        <div class="col-sm-5">
                            <input type="text" class="J_date" name="startDate" value="" />
                        </div>
                    </div>
                    <div class="form-group col-sm-6">
                        <label class="col-sm-2 control-label">结束时间</label>
                        <div class="col-sm-4">
                            <input type="text" class=" J_date" name="endDate" value="" />
                        </div>
                    </div>
                    <div class="form-group col-sm-6">
                        <label class="col-sm-4 control-label">物品名称</label>
                        <div class="col-sm-8">
                            <input type="text" class="w190" name="itemName" value="" />
                        </div>
                    </div>
                    <div class="form-group col-sm-6">
                        <label class="col-sm-2 control-label">供货商</label>
                        <div class="col-sm-8">
                            <select class="form-control w190" name="supplierId">
                                <option value="0">请选择</option>
                                <c:forEach items="${supplierList}" var="supplier">
                                    <option value="${supplier.id}">${supplier.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label padding-R">存放点</label>
                        <div class="J_checkbox checkbox block col-sm-7">
                            <c:forEach items="${storageDepotList}" var="depot">
                                <label>
                                    <input type="checkbox" name="depotIds" value="${depot.id}" />${depot.name}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label padding-R">分类</label>
                        <div class="checkbox block col-sm-7">
                            <c:forEach items="${tagList}" var="tag">
                                <label>
                                    <input type="checkbox" name="tagIds" value="${tag.id}" />${tag.name}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-5">
                        <button class="btn-primary btn J_search" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>库存</h4>
            </div>
            <div class="panel-body">
                <div class="text-right">
                    <a href="#?" class="btn btn-warning margin-bottom-15 J_export">
                        <i class="fa fa-download"></i>&nbsp;导出Excel
                    </a>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead class="v-center">
                        <tr>
                            <th rowspan="2">类别</th>
                            <th rowspan="2">编号</th>
                            <th rowspan="2">物品名称</th>
                            <th rowspan="2">物品最新入库单价</th>
                            <th rowspan="2">库存规格</th>
                            <th rowspan="2">订货规格</th>
                            <th colspan="2">期初</th>
                            <th colspan="2">入库</th>
                            <th colspan="2">出库</th>
                            <th colspan="2">盈亏</th>
                            <th colspan="3">结存</th>
                            <th colspan="2">库存报警值</th>
                        </tr>
                        <tr>
                            <th>数量</th>
                            <th>金额</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th>数量</th>
                            <th>均价</th>
                            <th>金额</th>
                            <th>上限</th>
                            <th>下限</th>
                        </tr>
                        </thead>
                        <tbody id="J_template">

                        </tbody>
                    </table>
                    <div class="J_pagination pagination-white">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li>
                <a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a>
            </li>
            <li>
                <a href="#">库存管理</a>
            </li>
            <li>
                <a href="#" class="active">库存盘点</a>
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
                    <div class="form-group "  >
                        <label class="col-sm-2 control-label">开始时间</label>
                        <div class="col-sm-7">
                            <input type="text" class="J_date J_startTime" name="startTime" value="${currentMonthFirstDay}" readonly="readonly" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">结束时间</label>
                        <div class="col-sm-7">
                            <input type="text" class=" J_date J_endTime" name="endTime" value="${currentMonthNowDay}"  readonly="readonly" />
                        </div>
                    </div>
                    <!-- 存放搜索结果的keword -->
                    <input type="hidden" class="w190 J_ingredientInp"  name="keyword" value="" />
                    <div class="form-group">
                        <label for="basic" class="col-sm-2 control-label">原配料名称</label>
                        <div class="col-sm-5 J_text" id="basic"></div>
                    </div>
                    <!-- 搜索原材料-hidden-刷数据 -->
                    <!-- data-code中刷原材料编号 -->
                    <select class="selectpicker show-tick form-control hidden" data-live-search="true">
                        <c:forEach var="ingredient" items="${ingredientList}">
                            <option value="${ingredient.id}" data-code="${ingredient.assistantCode}">${ingredient.name}</option>
                        </c:forEach>
                    </select>
                    <div class="form-group">
                        <label class="col-sm-2 control-label padding-R-17">分类</label>
                        <div class="checkbox block col-sm-7">
                            <label>
                                <!-- 添加了全选类class="J_selectAll" -->
                                <input type="checkbox" class="J_selectAll" name="tagIds" value="1">全选
                            </label>
                            <c:forEach var="tag" items="${tagList}">
                                <label>
                                    <input type="checkbox" class="J_selectType" name="tagIds"  value="${tag.id}" />${tag.name}
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
                    <!-- 添加了库存盘点类名J_inventory，href="javascript:;"-->
                    <a href="javascript:;" class="btn btn-warning margin-bottom-15 J_inventory">库存盘点</a>
                    <a href="${website}admin/storage/settlement/check/export" class="btn btn-warning margin-bottom-15 J_export"><i class="fa fa-download"></i>&nbsp;导出Excel</a>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead class="v-center">
                        <tr>
                            <th rowspan="2">序号</th>
                            <th rowspan="2">类别</th>
                            <th rowspan="2">名称</th>
                            <th rowspan="2">编号</th>
                            <th colspan="3">规格</th>
                            <th rowspan="2">期初</th>
                            <th rowspan="2">入库</th>
                            <th rowspan="2">出库</th>
                            <th rowspan="2">盈亏</th>
                            <th rowspan="2">结存</th>
                            <th colspan="2">库存报警值</th>
                        </tr>
                        <tr>
                            <th>库存</th>
                            <th>订货</th>
                            <th>成本卡</th>
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
</div><!--row-->
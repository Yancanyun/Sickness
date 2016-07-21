<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li>
                <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
            </li>
            <li>
                <a href="#">库存管理</a>
            </li>
            <li>
                <a href="#" class="active">库存单据管理</a>
            </li>
        </ol>
        <h2>库存单据管理</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <!-- 保存当前登录人信息 -->
                <input type="hidden" class="J_currentLoginUser" value="sadmin">
                <form class="form-horizontal J_searchForm" autocomplete="off">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">时间段</label>
                        <div class="col-sm-8">
                            <input type="text" class="J_startTime w180" data-start-time="${currentMonthFirstDay}" readonly="readonly" name="startTime" value="${currentMonthFirstDay}" />
                            <span class="to">~</span>
                            <input type="text" class="J_endTime w180" data-end-time="${currentDay}" readonly="readonly" name="endTime" value="${currentDay}" />
                            <span class="month J_shortcut" data-start-time="${currentMonthFirstDay}" data-end-time="${currentMonthLastDay}">本月</span>
                            <span class="month J_shortcut" data-start-time="${lastMonthFirstDay}" data-end-time="${lastMonthLastDay}">上月</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">经手人</label>
                        <div class="col-sm-10">
                            <select class="col-sm-2 form-control w180" name="handlerPartyId">
                                <option value="-1">请选择</option>
                                <c:forEach var="handler" items ="${handlerList}">
                                    <option value="${handler.employee.partyId}">${handler.employee.name}</option>
                                </c:forEach>
                            </select>
                            <label class="col-sm-2 control-label">操作人</label>
                            <select class="col-sm-2 form-control w180" name="createdPartyId">
                                <option value="-1">请选择</option>
                                <c:forEach var="created" items="${createdList}">
                                    <option value="${created.employee.partyId}">${created.employee.name}</option>
                                </c:forEach>
                            </select>
                            <label class="col-sm-2 control-label">审核人</label>
                            <select class="col-sm-2 form-control w180" name="auditPartyId">
                                <option value="-1">请选择</option>
                                <c:forEach var="audited" items="${auditedList}">
                                    <option value="${audited.employee.partyId}">${audited.employee.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">存放点</label>
                        <div class="col-sm-10">
                            <select class="col-sm-2 form-control w180" name="depotId">
                                <option value="-1">请选择</option>
                                <c:forEach var="deport" items="${depotList}">
                                    <option value="${deport.id}">${deport.name}</option>
                                </c:forEach>
                            </select>
                            <label class="col-sm-2 control-label">结算状态</label>
                            <select class="col-sm-2 form-control w180" name="isSettlemented">
                                <option value="-1">请选择</option>
                                <option value="0">未结算</option>
                                <option value="1">已结算</option>
                            </select>
                            <label class="col-sm-2 control-label">审核状态</label>
                            <select class="col-sm-2 form-control w180" name="isAudited">
                                <option value="-1">请选择</option>
                                <option value="0">未审核</option>
                                <option value="1">已通过</option>
                                <option value="2">未通过</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-5">
                        <button class="btn-primary btn" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>库存单据列表</h4>
            </div>
            <div class="panel-body">
                <p class="oper clearfix">
                    <a class="btn btn-success margin-bottom-15 J_inStoreBill" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加入库单</a>
                    <a class="btn btn-success margin-bottom-15 J_outStoreBill" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加出库单</a>
                    <a class="btn btn-success margin-bottom-15 J_getStoreBill" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加盘盈单</a>
                    <a class="btn btn-success margin-bottom-15 J_loseStoreBill" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加盘亏单</a>
                    <!-- modify -->
                    <a class="btn btn-warning margin-bottom-15 pull-right J_allExport" href="${website}admin/storage/report/exportall" target="_blank"><i class="fa fa-download"></i>&nbsp;全部导出excel</a>
                    <a class="btn btn-warning margin-bottom-15 margin-right-10 pull-right J_export" href="${website}admin/storage/report/exportbycd" target="_blank"><i class="fa fa-download"></i>&nbsp;按条件导出excel</a>
                </p>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th>类型</th>
                            <th>单据编号</th>
                            <th>经手人</th>
                            <th>操作人</th>
                            <th>审核人</th>
                            <th>结算状态</th>
                            <th>审核状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="J_template"></tbody>
                    </table>
                    <div class="J_pagination">
                    </div>
                    <!-- 搜索物品-hidden-刷数据 -->
                    <select class="selectpicker show-tick form-control hidden" data-live-search="true">
                        <c:forEach var="item" items="${itemList}">
                            <option value="${item.id}" data-price="${item.lastStockInPrice}" data-code="${item.assistantCode}" data-orderUnitName="${item.orderUnitName}" data-itemNumber="${item.itemNumber}">${item.name}</option>
                        </c:forEach>
                    </select>
                    <!-- modify -->
                    <!-- 搜索原材料-hidden-刷数据 -->
                    <select class="J_searchGoodsSelect selectpicker show-tick form-control hidden" data-live-search="true">
                        <c:forEach var="ingredient" items="${ingredientList}">
                            <option value="${ingredient.id}"  data-code="${ingredient.assistantCode}" data-costCardUnitName="${ingredient.costCardUnitName}"  data-ingredientNumber="${ingredient.ingredientNumber}">${ingredient.name}</option>
                        </c:forEach>
                    </select>
                    <!-- 存放点 -hidden-刷数据 -->
                    <select class="form-control w180 hidden J_depotSelect" name="depotId">
                        <c:forEach var="deport" items="${depotList}">
                            <option value="${deport.id}">${deport.name}</option>
                        </c:forEach>
                    </select>
                    <!-- 经手人 -hidden-刷数据 -->
                    <select class="form-control w180 hidden J_handlerSelect" name="handlerPartyId">
                        <c:forEach var="handler" items ="${handlerList}">
                            <option value="${handler.employee.partyId}">${handler.employee.name}</option>
                        </c:forEach>
                    </select>

                    <!-- 审核人-hidden-刷数据 -->
                    <select class="col-sm-2 form-control w180 hidden J_auditSelect" name="auditPartyId">
                        <c:forEach var="audited" items="${auditedList}">
                            <option value="${audited.employee.partyId}">${audited.employee.name}</option>
                        </c:forEach>
                    </select>
                    <!-- 结算状态-hidden-刷数据 -->
                    <select class="col-sm-2 form-control w180 hidden J_isSettleSelect" name="isSettlemented">
                        <option value="0">未结算</option>
                        <option value="1">已结算</option>
                    </select>
                    <!-- 审核状态-hidden-刷数据 -->
                    <select class="col-sm-2 form-control w180 hidden J_checkSelect" name="isAudited">
                        <option value="1">通过</option>
                        <option value="2">不通过</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div><!--row-->

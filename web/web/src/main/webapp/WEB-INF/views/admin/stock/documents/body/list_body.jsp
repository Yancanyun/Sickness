<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: wychen
  Date: 2017/3/29
  Time: 8:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
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
                    <form class="form-horizontal J_searchForm" autocomplete="off">
                        <div class="form-group">
                            <label class="col-sm-2 control-label billType ">单据类型</label>
                            <div class="col-sm-2">
                                <select class="col-sm-2 form-control w180" name="type">
                                    <option value="-1">请选择</option>
                                    <option value="1">入库单</option>
                                    <option value="2">领用单</option>
                                    <option value="3">领回单</option>
                                    <option value="4">盘盈单</option>
                                    <option value="5">盘亏单</option>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label">时间段</label>
                            <div class="col-sm-6">
                                <div class="billType">
                                    <input type="text" class="J_startTime w180" data-start-time="${currentMonthFirstDay}" readonly="readonly" name="startTime" value="${currentMonthFirstDay}" />
                                    <span class="to">~</span>
                                    <input type="text" class="J_endTime w180" data-end-time="${currentDay}" readonly="readonly" name="endTime" value="${currentDay}" />
                                    <span class="month J_shortcut" data-start-time="${currentMonthFirstDay}" data-end-time="${currentMonthFirstDay}">本月</span>
                                    <span class="month J_shortcut" data-start-time="${lastMonthFirstDay}" data-end-time="${lastMonthFirstDay}">上月</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">经手人</label>
                            <div class="col-sm-2">
                                <select class="col-sm-2 form-control w180" name="handlerPartyId">
                                    <c:if test="${fn:length(handlerList) == 0}">
                                        <option>无</option>
                                    </c:if>
                                    <c:if test="${fn:length(handlerList) > 0}">
                                        <c:forEach var="handler" items="${handlerList}">
                                            <option value="${handler.employee.id}">${handler.loginName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label">操作人</label>
                            <div class="col-sm-2">
                                <select class="col-sm-2 form-control w180" name="createdPartyId">
                                    <c:if test="${fn:length(createdList) == 0}">
                                        <option>无</option>
                                    </c:if>
                                    <c:if test="${fn:length(createdList) > 0}">
                                        <c:forEach var="creator" items="${createdList}">
                                            <option value="${creator.employee.id}">${creator.loginName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label">审核人</label>
                            <div class="col-sm-2">
                                <select class="col-sm-2 form-control w180" name="auditPartyId">
                                    <c:if test="${fn:length(auditedList) == 0}">
                                        <option>无</option>
                                    </c:if>
                                    <c:if test="${fn:length(auditedList) > 0}">
                                        <c:forEach var="auditor" items="${auditedList}">
                                            <option value="${auditor.employee.id}">${auditor.loginName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">存放点</label>
                            <div class="col-sm-2">
                                <select class="col-sm-2 form-control w180" name="kitchenId">
                                    <c:if test="${fn:length(kitchenList) == 0}">
                                        <option>无</option>
                                    </c:if>
                                    <c:if test="${fn:length(kitchenList) > 0}">
                                        <c:forEach var="kitchen" items="${kitchenList}">
                                            <option value="${kitchen.id}">${kitchen.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label">结算状态</label>
                            <div class="col-sm-2">
                                <select class=" form-control w180" name="isSettled">
                                    <option value="-1">请选择</option>
                                    <option value="0">未结算</option>
                                    <option value="1">已结算</option>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label">审核状态</label>
                            <div class="col-sm-2">
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
                        <a class="btn btn-success margin-bottom-15 J_inStoreBill" href="#"><i class="fa fa-plus"></i>&nbsp;添加入库单</a>
                        <a class="btn btn-success margin-bottom-15 J_outStoreBill" href="#"><i class="fa fa-plus"></i>&nbsp;添加领用单</a>
                        <a class="btn btn-success margin-bottom-15 J_backStoreBill" href="#"><i class="fa fa-plus"></i>&nbsp;添加领回单</a>
                        <a class="btn btn-success margin-bottom-15 J_checkStoreBill" href="#"><i class="fa fa-plus"></i>&nbsp;添加盘盈单</a>
                        <a class="btn btn-success margin-bottom-15 J_checkStoreBill" href="#"><i class="fa fa-plus"></i>&nbsp;添加盘亏单</a>
                        <a class="btn btn-warning margin-bottom-15 pull-right J_allExport" href="" target="_blank"><i class="fa fa-download"></i>&nbsp;全部导出excel</a>
                        <a class="btn btn-warning margin-bottom-15 margin-right-10 pull-right J_export" href="${website}admin/stock/documents/exportbycd" target="_blank"><i class="fa fa-download"></i>&nbsp;按条件导出excel</a>
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
                                <th>日期</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="J_template"></tbody>
                        </table>
                        <div class="J_pagination">
                        </div>
                        <!-- 审核人-hidden-刷数据 -->
                        <select class="col-sm-2 form-control w180 hidden J_auditSelect" name="auditPartyId">
                            <option value="-1">请选择</option>
                            <option value="0">审核人0</option>
                            <option value="1">审核人1</option>
                            <option value="2">审核人2</option>
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
</div><!--container-->

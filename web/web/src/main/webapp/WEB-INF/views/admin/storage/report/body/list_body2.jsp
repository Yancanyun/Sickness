<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                        <label class="col-sm-2 control-label">存放点</label>
                        <div class="J_checkbox checkbox block col-sm-7">
                            <label>
                                <input class="J_selectAll" type="checkbox" name="depotId" value="0" />全部
                            </label>
                            <c:forEach var="deport" items="${depotList}">
                                <label>
                                    <input class="J_depot" type="checkbox" name="depotId" value="${deport.id}" />${deport.name}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">时间段</label>
                        <div class="col-sm-8">
                            <input type="text" class="J_startTime w180" data-start-time="2014-8-01" readonly="readonly" name="startTime" value="2014-8-01" />
                            <span class="to">~</span>
                            <input type="text" class="J_endTime w180" data-end-time="2015-9-11" readonly="readonly" name="endTime" value="2015-9-11" />
                            <span class="month J_shortcut" data-start-time="2015-10-01" data-end-time="2015-10-31">本月</span>
                            <span class="month J_shortcut" data-start-time="2015-09-01" data-end-time="2015-09-30">上月</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">经手人</label>
                        <div class="col-sm-8">
                            <select class="form-control w180" name="handlerPartyId">
                                <option value="">请选择</option>
                                <c:forEach var="handler" items="${handlerList}">
                                    <option value="${handler.employee.partyId}">${handler.employee.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">当事人</label>
                        <div class="col-sm-8">
                            <select class="form-control w180" name="createdPartyId">
                                <option value="">请选择</option>
                                <c:forEach var="created" items="${createdList}">
                                    <option value="${created.employee.partyId}">${created.employee.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-3">
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
                    <a class="btn btn-warning margin-bottom-15 pull-right J_export" href="#?" target="_blank"><i class="fa fa-download"></i>&nbsp;导出excel</a>
                </p>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th>类型</th>
                            <th>单据编号</th>
                            <th>存放点</th>
                            <th>经手人</th>
                            <th>金额</th>
                            <th>操作人</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="J_template">



                        </tbody>
                    </table>
                    <div class="J_pagination">
                    </div>
                    <select class="selectpicker show-tick form-control hidden" data-live-search="true">
                        <option value="1" data-price="01" data-code="c">cow</option>
                        <option value="2" data-price="02" data-code="b">bull</option>
                        <option value="3" data-price="03" data-code="o">ox</option>
                        <option value="4" data-price="04" data-code="A">ASD</option>
                        <option value="5" data-price="05" data-code="B">Bla</option>
                        <option value="6" data-price="06" data-code="B">Ble</option>
                        <option value="6" data-price="06" data-code="B">Ble</option>
                        <option value="6" data-price="06" data-code="B">Ble</option>
                        <option value="6" data-price="06" data-code="B">Ble</option>
                        <option value="6" data-price="06" data-code="B">Ble</option>
                    </select>
                    <select class="form-control w180 hidden J_depotSelect" name="depotId">
                        <option value="1">存放点1</option>
                        <option value="2">存放点2</option>
                        <option value="3">存放点3</option>
                        <option value="4">存放点4</option>
                    </select>
                    <select class="form-control w180 hidden J_handlerSelect" name="handlerPartyId">
                        <option value="1">经手人1</option>
                        <option value="2">经手人2</option>
                        <option value="3">经手人3</option>
                        <option value="4">经手人4</option>
                    </select>
                    <select class="form-control w180 hidden J_createSelect" name="createdPartyId">
                        <option value="0">操作人0</option>
                        <option value="1">操作人1</option>
                        <option value="2">操作人2</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div><!--row-->
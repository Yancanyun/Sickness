<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
      </li>
      <li>
        <a href="#">饭店管理</a>
      </li>
      <li class="active">餐台管理</li>
    </ol>
    <h2>餐台管理-餐台列表</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-success col-sm-12 J_msg" role="alert">${msg}</div>
    </c:if>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>搜索</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_searchForm">
          <div class="form-group">
            <label class="col-sm-3 control-label">选择区域</label>
            <div class="checkbox block col-sm-9">
              <label>
                <input class="J_selectAll"  type="checkbox" value="-1" name="areaId"> 全部区域
              </label>
              <c:forEach var="area" items="${areaList}">
                <label>
                  <input class="J_area" type="checkbox" name="areaId" value="${area.id}"> ${area.name}
                </label>
              </c:forEach>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">选择状态</label>
            <div class="col-sm-3">
              <select class="form-control" name="status">
                <option value="-1">请选择</option>
                <option value="0">停用</option>
                <option value="1">可用</option>
                <option value="2">占用已结账</option>
                <option value="3">占用未结账</option>
                <option value="4">已并桌</option>
                <option value="5">已预订</option>
              </select>
            </div>
          </div>
          <div class="col-sm-6 col-sm-offset-3">
            <div class="btn-toolbar">
              <button class="btn-primary btn J_searchBtn" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>餐台管理</h4>
      </div>
      <div class="panel-body">
        <a class="btn btn-success margin-bottom-15 J_add" href="${website}admin/restaurant/table/new"><i class="fa fa-plus"></i>&nbsp;添加餐台</a>
        <a class="btn btn-danger margin-bottom-15 J_batchDelete" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除餐台</a>
        <form class="J_operForm" autocomplete="off">
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>
                  <input class="J_selectAllTable"type="checkbox"/>
                </th>
                <th>所属区域</th>
                <th>餐台名称</th>
                <th>标准座位数</th>
                <th>使用状态</th>
                <th>餐位费用</th>
                <th>餐台费用</th>
                <th>最低消费</th>
                <th>餐段</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody id="J_template">
              <c:forEach var="tableDto" items="${tableDtoList}">
              <tr data-table-id="${tableDto.table.id}">
                <td><input class="J_table" type="checkbox"/></td>
                <td>${tableDto.areaName}</td>
                <td>${tableDto.table.name}</td>
                <td>${tableDto.table.seatNum}</td>
                <td class="J_status">${tableDto.table.statusStr}</td>
                <td>${tableDto.table.seatFee}元/人</td>
                <td>${tableDto.table.tableFee}元</td>
                <td>${tableDto.table.minCost}元</td>
                <td>
                  <c:if test="${!empty tableDto.mealPeriodList}">
                    <c:forEach var="mealPeriod" items="${tableDto.mealPeriodList}">
                      ${mealPeriod.name}
                    </c:forEach>
                  </c:if>
                  <c:if test="${empty tableDto.mealPeriodList}">无</c:if>
                </td>
                <td>
                  <a class="label-info J_edit" href="javascript:;" src="${website}admin/restaurant/table/update/${tableDto.table.id}"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                  <c:choose>
                    <c:when test="${tableDto.table.status == 0}">
                      <a class="label-info J_change" href="javascript:;"><i class="fa fa-check"></i>&nbsp;启用</a>
                    </c:when>
                    <c:otherwise>
                      <a class="label-info J_change" href="javascript:;"><i class="fa fa-circle"></i>&nbsp;停用</a>
                    </c:otherwise>
                  </c:choose>
                  <a class="label-info J_del" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
                </td>
              </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
        </form>
      </div>
    </div>
  </div>
</div><!--row-->
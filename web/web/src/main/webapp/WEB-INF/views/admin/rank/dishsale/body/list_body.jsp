<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <ol class="breadcrumb">
            <li>
              <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
            </li>
            <li>
              <a href="#">营业分析</a>
            </li>
            <li>
              <a href="#" class="active">菜品销售排行</a>
            </li>
          </ol>
          <h2>菜品销售排行</h2>
        </div>
        <div class="col-sm-12">
          <div class="panel panel-info">
            <div class="panel-heading">
              <h4>搜索</h4>
            </div>
            <div class="panel-body">
              <form class="form-horizontal J_searchForm col-sm-offset-1">
                <div class="form-group">
                  <label class="col-sm-2 control-label">时间段</label>
                  <div class="col-sm-8">
                    <input type="text" class="J_startTime w180" data-start-time="${firstToday}" readonly="readonly" name="startTime" value="${firstToday}" />
                    <span class="to">~</span>
                    <input type="text" class="J_endTime w180" data-end-time="${lastToday}" readonly="readonly" name="endTime" value="${lastToday}" />
                  </div>
                </div>
                <div class="form-group " >
                  <label class="col-sm-2 control-label">&nbsp;</label>
                  <div class="col-sm-8">
                    <span class="timing J_shortcut" data-start-time="${firstToday}" data-end-time="${lastToday}">今天</span>
                    <span class="timing J_shortcut" data-start-time="${firstYesterday}" data-end-time="${lastYesterday}">昨天</span>
                    <span class="timing J_shortcut" data-start-time="${currentWeekFirstDay}" data-end-time="${currentWeekLastDay}">本周</span>
                    <span class="timing J_shortcut" data-start-time="${lastWeekFirstDay}" data-end-time="${lastWeekLastDay}">上周</span>
                    <span class="timing J_shortcut" data-start-time="${currentMonthFirstDay}" data-end-time="${currentMonthLastDay}">本月</span>
                    <span class="timing J_shortcut" data-start-time="${lastMonthFirstDay}" data-end-time="${lastMonthLastDay}">上月</span>
                    <span class="timing J_shortcut" data-start-time="${currentYearFirstDay}" data-end-time="${currentYearLastDay}">整年</span>
                    <span class="timing J_shortcut" data-start-time="${LastYearFirstDay}" data-end-time="${LastYearLastDay}">去年</span>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label padding-R-17">菜品大类</label>
                  <div class="checkbox block col-sm-7">
                    <label>
                      <input type="checkbox" class="J_selectAll" name="tagIds" value="1">全选
                    </label>
                    <c:forEach items="${tag}" var="tag">
                      <label>
                        <input type="checkbox" class="J_selectType" name="tagIds" value="${tag.id}" />${tag.name}
                      </label>
                    </c:forEach>
                  </div>
                </div>
                <div class="col-sm-6 col-sm-offset-4">
                  <button class="btn-primary btn J_search" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="col-sm-12">
          <div class="panel panel-info">
            <div class="panel-heading">
              <h4>菜品销售排行</h4>
            </div>
            <div class="panel-body">
              <div class="text-right">
                <a href="${website}admin/rank/sale/exportToExcel" class="btn btn-warning margin-bottom-15 J_export"><i class="fa fa-download"></i>&nbsp;导出Excel</a>
              </div>
              <form class="J_operForm">
                <div class="table-responsive">
                  <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                      <th>菜品名称</th>
                      <th>菜品大类</th>
                      <th class="order pagination-reorder" data-order-key="num">销售数量</th>
                      <th class="order pagination-reorder" data-order-key="consumeSum">消费金额</th>
                    </tr>
                    </thead>
                    <tbody id="J_template"></tbody>
                  </table>
                  <div class="J_pagination pagination-white">
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>


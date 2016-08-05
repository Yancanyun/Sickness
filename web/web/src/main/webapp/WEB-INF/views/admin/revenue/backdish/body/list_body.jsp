<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

      <div class="row">
        <div class="col-sm-12">
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">营业分析</a></li>
            <li><a href="#" class="active">退菜报表</a></li>
          </ol>
          <h2>退菜报表</h2>
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
                    <input type="text" class="w180 date J_startTime" readonly="readonly" name="startTime" value="2014-8-01" />
                    <span class="to">~</span>
                    <input type="text" class="w180 date J_endTime" readonly="readonly" name="endTime" value="2015-9-11" />
                  </div>
                </div>
                <div class="form-group" >
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
                      <input type="checkbox" class="J_selectAll" name="tagId" value="0">全选
                    </label>
                    <c:forEach items="${list}" var="tag">
                      <label>
                        <input type="checkbox" class="J_bigTag" name="tagId"  value="${tag.id}" />${tag.name}
                      </label>
                    </c:forEach>
                  </div>
                </div>
                  <div class="col-sm-6 col-sm-offset-4">
                    <button class="btn-primary btn J_searchBtn" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                  </div>
              </form>
            </div>
          </div>
        </div>
        <div class="col-sm-12">
          <div class="panel panel-info">
            <div class="panel-heading">
              <h4>列表</h4>
            </div>
            <div class="panel-body">
              <div class="text-right">
                <a href="${website}admin/revenue/backdish/exportToExcel" class="btn btn-warning margin-bottom-15 J_export"><i class="fa fa-download"></i>&nbsp;导出Excel</a>
              </div>
              <form class="J_operForm">
                <div class="table-responsive">
                  <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                      <th>编号</th>
                      <th>日期</th>
                      <th>点菜时间</th>
                      <th>退菜时间</th>
                      <th>间隔时间</th>
                      <th>退菜人</th>
                      <th>餐台</th>
                      <th>菜品名称</th>
                      <th>菜品单价</th>
                      <th>退菜数量</th>
                      <th>金额</th>
                      <th>原因</th>
                    </tr>
                    </thead>
                    <tbody id="J_template"></tbody>
                  </table>
                  <div class="J_pagination pagination-white"></div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>

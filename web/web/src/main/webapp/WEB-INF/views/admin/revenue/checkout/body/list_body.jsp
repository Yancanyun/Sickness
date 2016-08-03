<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a>
      </li>
      <li>
        <a href="#">营业分析</a>
      </li>
      <li>
        <a href="#" class="active">账单稽核</a>
      </li>
    </ol>
    <h2>账单稽核</h2>
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
              <span class = "timing J_shortcut" data-start-time = "${firstToday}" data-end-time = "${lastToday}">今天</span>
              <span class = "timing J_shortcut" data-start-time = "${firstYesterday}" data-end-time = "${lastYesterday}">昨天</span>
              <span class = "timing J_shortcut" data-start-time = "${currentWeekFirstDay}" data-end-time = "${currentWeekLastDay}">本周</span>
              <span class = "timing J_shortcut" data-start-time = "${lastWeekFirstDay}" data-end-time = "${lastWeekLastDay}">上周</span>
              <span class = "timing J_shortcut" data-start-time = "${currentMonthFirstDay}" data-end-time = "${currentMonthLastDay}">本月</span>
              <span class = "timing J_shortcut" data-start-time = "${lastMonthFirstDay}" data-end-time = "${lastMonthLastDay}">上月</span>
              <span class = "timing J_shortcut" data-start-time = "${currentYearFirstDay}" data-end-time = "${currentYearLastDay}">整年</span>
              <span class = "timing J_shortcut" data-start-time = "${LastYearFirstDay}" data-end-time = "${LastYearLastDay}">去年</span>
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
        <h4>账单统计</h4>
      </div>
      <div class="panel-body">
        <div class="text-right">
          <a href="?sdff" class="btn btn-warning margin-bottom-15 J_export"><i class="fa fa-download"></i>&nbsp;导出Excel</a>
        </div>
        <form class="J_operForm">
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>账单编号</th>
                <th>餐台号</th>
                <th>餐台名称</th>
                <th>收银员</th>
                <th>结账时间</th>
                <th>支付类型</th>
                <th>消费金额</th>
                <th>抹零金额</th>
                <th>宾客付款</th>
                <th>找零金额</th>
                <th>实付金额</th>
                <th>发票</th>
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
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>总计</h4>
      </div>
      <div class="panel-body">
        <form class="J_sum">
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <!-- modify -->
              <thead>
              <tr>
                <th rowspan="2">账单总计</th>
                <th colspan="5">支付类型</th>
                <th rowspan="2">消费金额</th>
                <th rowspan="2">抹零金额</th>
                <th rowspan="2">宾客付款</th>
                <th rowspan="2">找零金额</th>
                <th rowspan="2">实付金额</th>
                <th rowspan="2">发票</th>
              </tr>
              <tr>
                <th>现金</th>
                <th>会员卡</th>
                <th>银行卡</th>
                <th>支付宝</th>
                <th>微信支付</th>
              </tr>
              </thead>
              <tbody id="dataRenderSum"></tbody>
              <!-- modify -->
            </table>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>



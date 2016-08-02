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
              <input type="text" class="J_startTime w180" data-start-time="2014-8-01" readonly="readonly" name="startTime" value="2014-8-01" />
              <span class="to">~</span>
              <input type="text" class="J_endTime w180" data-end-time="2015-9-11" readonly="readonly" name="endTime" value="2015-9-11" />
            </div>
          </div>
          <div class="form-group " >
            <label class="col-sm-2 control-label">&nbsp;</label>
            <div class="col-sm-8">
              <span class="timing J_shortcut" data-start-time="2015-10-01" data-end-time="2015-10-01">今天</span>
              <span class="timing J_shortcut" data-start-time="2015-09-30" data-end-time="2015-09-30">昨天</span>
              <span class="timing J_shortcut" data-start-time="2015-10-01" data-end-time="2015-10-08">本周</span>
              <span class="timing J_shortcut" data-start-time="2015-09-23" data-end-time="2015-10-01">上周</span>
              <span class="timing J_shortcut" data-start-time="2015-10-01" data-end-time="2015-10-31">本月</span>
              <span class="timing J_shortcut" data-start-time="2015-09-01" data-end-time="2015-09-30">上月</span>
              <span class="timing J_shortcut" data-start-time="2015-10-01" data-end-time="2016-10-01">整年</span>
              <span class="timing J_shortcut" data-start-time="2014-10-01" data-end-time="2015-10-01">去年</span>
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
                <th rowspan="2">账单编号</th>
                <th colspan="2">餐台</th>
                <th rowspan="2">收银员</th>
                <th rowspan="2">结账时间</th>
                <th colspan="5">支付类型</th>
                <th rowspan="2">消费金额</th>
                <th rowspan="2">抹零金额</th>
                <th rowspan="2">宾客付款</th>
                <th rowspan="2">找零金额</th>
                <th rowspan="2">实付金额</th>
                <th rowspan="2">发票</th>
              </tr>
              <tr>
                <th>台号</th>
                <th>名称</th>
                <th>现金</th>
                <th>会员卡</th>
                <th>银行卡</th>
                <th>支付宝</th>
                <th>微信支付</th>
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
              <thead>
              <tr>
                <th rowspan="2">账单编号</th>
                <th colspan="2">餐台</th>
                <th rowspan="2">收银员</th>
                <th rowspan="2">结账时间</th>
                <th colspan="5">支付类型</th>
                <th rowspan="2">消费金额</th>
                <th rowspan="2">抹零金额</th>
                <th rowspan="2">宾客付款</th>
                <th rowspan="2">找零金额</th>
                <th rowspan="2">实付金额</th>
                <th rowspan="2">发票</th>
              </tr>
              <tr>
                <th>台号</th>
                <th>名称</th>
                <th>现金</th>
                <th>会员卡</th>
                <th>银行卡</th>
                <th>支付宝</th>
                <th>微信支付</th>
              </tr>
              </thead>
              <tbody id="J_template"></tbody>
            </table>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>



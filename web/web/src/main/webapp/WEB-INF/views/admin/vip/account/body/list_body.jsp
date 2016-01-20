<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="">会员管理</a></li>
      <li class="active">会员账户信息管理</li>
    </ol>
    <h2>会员账户信息管理</h2>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>列表</h4>
      </div>
      <div class="panel-body">
        <form class="J_operForm">
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th class="order pagination-reorder" data-order-key="minConsumption">会员等级</th>
                <th>姓名</th>
                <th>电话</th>
                <th>卡号</th>
                <th class="order pagination-reorder" data-order-key="balance">卡内余额</th>
                <th>会员积分</th>
                <th>总消费额</th>
                <th>已挂账金额</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody id="J_template">
              </tbody>
            </table>
            <div class="J_pagination"></div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
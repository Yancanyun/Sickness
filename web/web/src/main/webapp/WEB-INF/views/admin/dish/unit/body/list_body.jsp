<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
      </li>
      <li>
        <a href="#">菜品管理</a>
      </li>
      <li class="active">单位管理</li>
    </ol>
    <h2>单位管理</h2>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>单位列表</h4>
      </div>
      <div class="panel-body">
        <a class="btn btn-success margin-bottom-15 J_addBtn" href="javascript:;">
          <i class="fa fa-plus"></i>&nbsp;添加单位
        </a>
        <form class="J_operForm">
          <input type = "hidden" class="J_id" name="id" value=""/>
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th class="col-sm-1">分类</th>
                <th class="col-sm-1">单位</th>
                <th class="col-sm-1">操作</th>
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
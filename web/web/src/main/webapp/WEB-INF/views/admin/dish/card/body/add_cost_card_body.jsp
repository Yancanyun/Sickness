<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="#"><i class="fa fa-home">&nbsp;首页</i></a>
      </li>
      <li>
        <a href="#">菜品管理</a>
      </li>
      <li class="active">菜品成本卡管理</li>
    </ol>
    <h2>添加菜品成本卡</h2>
    <!-- 如果是修改页的话显示下面这两个 -->
    <!-- <h2>修改菜品成本卡</h2> -->
    <!--<input type="hidden" name="id" value="成本卡Id"/>-->
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>菜品名称</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_operForm">
          <div class="form-group">
            <label for="basic" class="col-sm-3 control-label"><span class="requires">*</span>菜品名称</label>
            <div class="col-sm-4" id="basic"></div>
          </div>
          <input type="hidden" class="J_dishId" value="" />
        </form>
      </div>
    </div>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>成本卡明细</h4>
      </div>
      <div class="panel-body">
        <a class="btn btn-success margin-bottom-15" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加原材料</a>
        <div class="table-responsive">
          <table class="table table-hover table-bordered">
            <thead>
            <tr>
              <th>原材料</th>
              <th>材料类别</th>
              <th>单位</th>
              <th>毛料用量</th>
              <th>净料用量</th>
              <th>班结时自动出库</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody class="J_tbody">
            <tr>
              <th>猪肉</th>
              <th>主料</th>
              <th>克</th>
              <th>500</th>
              <th>400</th>
              <th>是</th>
              <th>操作</th>
            </tr>
            </tbody>
          </table>
          <select class="selectpicker show-tick form-control hidden" data-live-search="true">
            <!-- 把菜品id写到value里面 -->
            <option value="1" data-code="c">cow</option>
            <option value="2" data-code="b">bull</option>
            <option value="3" data-code="o">ox</option>
            <option value="4" data-code="A">ASD</option>
            <option value="5" data-code="B">Bla</option>
            <option value="6" data-code="B">Ble</option>
          </select>
        </div>
      </div>
    </div>
  </div>
</div>


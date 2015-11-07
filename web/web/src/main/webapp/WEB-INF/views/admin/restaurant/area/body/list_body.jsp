<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">饭店管理</a></li>
      <li class="active">餐台区域管理</li>
    </ol>
    <h2>餐台区域管理-餐台区域列表</h2>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>餐台区域列表</h4>
      </div>
      <div class="panel-body">
        <a class="btn btn-success margin-bottom-15 J_addBtn" href="#"><i class="fa fa-plus"></i>添加餐台区域</a>
        <form class="J_operForm">
          <!-- 使用隐藏input存放id -->
          <input type = "hidden" id="J_hiddenId"name="id" value=""/>
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>餐台区域</th>
                <th>权重</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody class="data-show-tbody">
              <c:forEach var="area" items="${areaList}">
                <tr table-area-id = "${area.id}">
                  <td class="J_desc col-sm-8">${area.name}</td>
                  <td class="J_weight col-sm-2">${area.weight}</td>
                  <td class="col-sm-4">
                    <a class="label-info J_editBtn" href="javascript:;"><i class="fa fa-pencil"></i>&nbsp;编辑</a>&nbsp;
                    <a class="label-info J_delBtn" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>&nbsp;
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
</div><!-- row -->
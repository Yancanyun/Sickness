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
		<h2>菜品成本卡管理</h2>
	</div>
	<div class="col-sm-12">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4>搜索</h4>
			</div>
			<div class="panel-body">
				<form class="form-horizontal J_operForm">
					<div class="form-group">
						<label class="col-sm-3 control-label">关键字</label>
						<div class="col-sm-3">
							<input type="text" class="form-control w200" placeholder="请输入名称/助记码" name="keyword" value="">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">选择菜品分类</label>
						<div class="col-sm-6">
							<div class="checkbox block">
								<label>
									<input class="J_selectAll"  type="checkbox" value="0" name="bigTag"> 全部大类
								</label>
								<c:forEach var="tag" items="${tagList}">
								<label>
									<input class="J_selectAll"  type="checkbox" value="${tag.id}" name="tagIdList"> ${tag.name}
								</label>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-sm-offset-3">
						<div class="btn-toolbar">
							<button class="btn-primary btn" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-sm-12">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4>菜品成本卡列表</h4>
			</div>
			<div class="panel-body">
				<a class="btn btn-success margin-bottom-15" href="card/add/cost/card"><i class="fa fa-plus"></i>&nbsp;添加成本卡</a>
				<div class="table-responsive">
					<table class="table table-hover table-bordered">
						<thead>
						<tr>
							<th>成本卡编号</th>
							<th>菜品名称</th>
							<th>助记码</th>
							<th>主料成本</th>
							<th>辅料成本</th>
							<th>调料成本</th>
							<th>标准成本</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody id="J_template"></tbody>
					</table>
					<div class="J_pagination">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
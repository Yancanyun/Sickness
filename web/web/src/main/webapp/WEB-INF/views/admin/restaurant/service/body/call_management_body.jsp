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
			<li class="active">呼叫服务类型管理</li>
		</ol>
		<h2>呼叫服务类型管理</h2>
		<div class="alert alert-success col-sm-12
							J_msg" role="alert">添加成功！</div>
		<!-- <div class="alert alert-danger col-sm-12 J_msg" role="alert">添加失败！</div> -->
	</div>
	<div class="col-sm-12">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4>呼叫服务类型管理</h4>
			</div>
			<div class="panel-body">
				<a href="javascript:;" class="btn btn-success margin-bottom-15 J_add"><i class="fa fa-plus"></i>&nbsp;添加呼叫服务类型</a>
				<form class="J_operForm" method="post" action="${website}admin/restaurant/call/waiter">
					<input class="J_hidden" type="hidden" name="id" value=""/>
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
							<thead>
							<tr>
								<th>名称</th>
								<th>状态</th>
								<th>排序</th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody id="J_template">
							<c:forEach var="callWaiter" items="${callWaiter}">
								<tr data-call-id="${callWaiter.id}">
									<td class="J_name">${callWaiter.name}</td>
									<c:choose>
										<c:when test="${callWaiter.status eq 1}">
											<td class="J_status">启用</td>
										</c:when>
										<c:otherwise>
											<td class="J_status">停用</td>
										</c:otherwise>
									</c:choose>
									<td class="J_weight">${callWaiter.weight}</td>
									<td>
										<a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
										<c:choose>
											<c:when test="${callWaiter.status eq 1}">
												<a href="javascript:;" class="label-info J_change"><i class="fa fa-circle"></i>&nbsp;停用</a>
											</c:when>
											<c:otherwise>
												<a href="javascript:;" class="label-info J_change"><i class="fa fa-check"></i>&nbsp;启用</a>
											</c:otherwise>
										</c:choose>
										<!--<a href="javascript:;" class="label-info J_change"><i class="fa fa-check"></i>&nbsp;启用</a>-->
										<a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
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
</div>
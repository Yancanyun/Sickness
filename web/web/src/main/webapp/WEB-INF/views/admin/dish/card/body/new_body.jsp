<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
	<div class="col-sm-12">
		<ol class="breadcrumb">
			<li>
				<a href="${website}admin"><i class="fa fa-home">&nbsp;首页</i></a>
			</li>
			<li>
				<a href="#">菜品管理</a>
			</li>
			<li class="active">菜品成本卡管理</li>
		</ol>
		<h2>添加菜品成本卡</h2>
		<!-- <h2>修改菜品成本卡</h2> -->
	</div>
	<div class="col-sm-12">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4>添加菜品成本卡</h4>
			</div>
			<div class="panel-body">
				<form class="form-horizontal J_operForm">
					<h4>菜品名称</h4>
					<hr/>
					<div class="form-group">
						<label for="basic" class="col-sm-3 control-label"><span class="requires">*</span>菜品名称</label>
						<div class="col-sm-3" id="basic"></div>
						<a class="btn btn-success margin-bottom-15 J_add" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加原材料</a>
					</div>
					<input type="hidden" name="id" Class="J_costCardId" value=""/>
					<input type="hidden" class="J_dishId" name="dishId" value="" />
					<h4>原配料管理</h4>
					<hr/>
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

							</tbody>
						</table>
					</div>
					<h4>成本计算<span class="tip">（该成本根据原配料自动计算，您也可以手动修改）</span></h4>
					<hr/>
					<div class="form-group">
						<label class="col-sm-3 control-label">主料成本</label>
						<div class="col-sm-6">
							<input class="w200 J_price J_mainCost" type="text" data-valid-rule="isNull | notNegativeNumber " data-valid-tip="请输入大于0的数字|请输入大于0的数字" name="mainCost" value="">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">辅料成本</label>
						<div class="col-sm-6">
							<input class="w200 J_price J_assistCost" type="text"  name="assistCost" value="" data-valid-rule="isNull | notNegativeNumber " data-valid-tip="请输入大于0的数字|请输入大于0的数字">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">调料成本</label>
						<div class="col-sm-6">
							<input class="w200 J_price J_deliciousCost" type="text" data-valid-rule="isNull | notNegativeNumber " data-valid-tip="请输入大于0的数字|请输入大于0的数字"  name="deliciousCost" value="">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">标准成本</label>
						<div class="col-sm-6">
							<input class="w200 J_standardCost" readonly type="text" value="">
						</div>
					</div>
				</form>
			</div>
			<div class="panel-footer">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<div class="btn-toolbar">
							<button class="btn-primary btn J_submitBtn" type="submit" data-btn-type="loading" data-btn-loading-text="正在保存，请稍后">
								<i class="fa fa-save"></i>
								&nbsp;保存
							</button>
							<button class="btn-default btn" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 页面第一个加载时，把菜品刷到这个select里面来 -->
	<select class="J_dishSelect show-tick form-control hidden" data-live-search="true">
		<!-- 把菜品id写到value里面 -->
		<c:forEach items="${dishList}" var="dish">
			<option value="${dish.id}" data-code="${dish.assistantCode}">${dish.name}</option>
		</c:forEach>
		<%--<option value="2" data-code="b">bull</option>
		<option value="3" data-code="o">ox</option>
		<option value="4" data-code="A">ASD</option>
		<option value="5" data-code="B">Bla</option>
		<option value="6" data-code="B">Ble</option>--%>
	</select>
	<!-- 页面一加载的时候把原配料刷在这个select里面 -->
	<select class="J_ingredientSelect show-tick form-control hidden" data-live-search="true">
		<!-- 把id写到value里面 -->
		<c:forEach items="${ingredientList}" var="ingredient">
			<option value="${ingredient.id}" data-code="${ingredient.assistantCode}" data-unit="${ingredient.costCardUnitName}">${ingredient.name}</option>
		</c:forEach>
		<%--<option value="1" data-code="" data-unit="克">l</option>
		<option value="2" data-code="i" data-unit="克">i</option>
		<option value="3" data-code="a" data-unit="克">a</option>
		<option value="4" data-code="n" data-unit="克">n</option>
		<option value="5" data-code="g" data-unit="克">g</option>
		<option value="6" data-code="x" data-unit="克">x</option>--%>
	</select>
</div><!--row-->
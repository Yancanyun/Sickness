<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/2
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li>
        <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
      </li>
      <li>
        <a href="#">会员管理</a>
      </li>
      <li class="active">
        会员价管理
      </li>
    </ol>
    <h2>会员价管理</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-success col-sm-12 J_msg" role="alert">${msg}</div>
    </c:if>
    <!-- <div class="alert alert-danger col-sm-12 J_msg" role="alert">添加失败！</div> -->
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>搜索</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_searchForm" method="${website}admin/vip/price/ajax/list" action="post">
          <input class="J_planIdHidden" type="hidden" name="vipDishPricePlanId" value="${vipDishPricePlan.id}"/>
          <div class="form-group">
            <label class="col-sm-3 control-label">关键字</label>
            <div class="col-sm-6">
              <input type="text" class="form-control w200" placeholder="请输入名称/编号/助记码" name="keyword" value="" />
            </div>
          </div>
          <div class="col-sm-6 col-sm-offset-3">
            <div class="btn-toolbar">
              <button class="btn-primary btn J_search" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>会员价方案：<span>${vipDishPricePlan.name}</span></h4>
      </div>
      <div class="panel-body">
        <a href="javascript:;" class="btn btn-success margin-bottom-15 J_generate"><i class="fa fa-plus"></i>&nbsp;自动生成会员价</a>
        <form class="J_operForm" method="" action=""  autocomplete="off">
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>
                  <input class="J_selectAll"type="checkbox"/>
                </th>
                <th>编号</th>
                <th>商品名称</th>
                <th>定价</th>
                <th>售价</th>
                <th>会员价</th>
                <th>差价</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody id="J_template">
              <c:forEach items="${vipDishPriceDtoList}" var="vipDishPriceDto">
                <tr data-dish-id="${vipDishPriceDto.dishId}">
                  <td>
                    <input class="J_price" type="checkbox" value="${vipDishPriceDto.dishId}" name="dishIdList"/>
                  </td>
                  <td>${vipDishPriceDto.dishNumber}</td>
                  <td>${vipDishPriceDto.dishName}</td>
                  <td>${vipDishPriceDto.price}</td>
                  <td>${vipDishPriceDto.salePrice}</td>
                  <td class="J_vipDishPrice">${vipDishPriceDto.vipDishPrice}</td>
                  <td>
                    <c:choose>
                      <c:when test="${vipDishPriceDto.vipDishPrice == null}">
                        &nbsp;
                      </c:when>
                      <c:when test="${vipDishPriceDto.price - vipDishPriceDto.vipDishPrice > 0}">
                        <i class="fa fa-long-arrow-down"></i>&nbsp;${vipDishPriceDto.difference}
                      </c:when>
                      <c:otherwise>
                        <i class="fa fa-long-arrow-up"></i>&nbsp;${vipDishPriceDto.difference}
                      </c:otherwise>
                    </c:choose>
                    <!-- <i class="fa fa-long-arrow-up"></i>&nbsp;0 -->
                  </td>
                  <td><a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a></td>
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
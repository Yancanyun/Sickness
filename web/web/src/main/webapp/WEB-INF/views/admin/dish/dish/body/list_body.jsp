<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="${website}admin/dish">菜品管理</a></li>
            <li class="active">菜品管理</li>
        </ol>
        <h2>菜品管理-菜品列表</h2>

    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_searchForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">关键字</label>

                        <div class="col-sm-6">
                            <input class="form-control w200" type="text" value="" name="keyword"
                                   placeholder="请输入名称/编号/助记码"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">选择菜品分类</label>

                        <div class="col-sm-6">
                            <div class="checkbox block">
                                <label>
                                    <input class="J_selectAll" type="checkbox" value="0" name="bigTag"> 全部大类
                                </label>
                                <c:forEach var="tag" items="${tagList}">
                                    <label>
                                        <input class="J_selectAll" type="checkbox" value="${tag.id}" name="tagIdList"> ${tag.name}
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
                <h4>菜品列表</h4>
            </div>
            <div class="panel-body">
                <a class="btn btn-success margin-bottom-15" href="${website}admin/dish/new"><i class="fa fa-plus"></i>&nbsp;添加菜品</a>

                <form class="J_operForm">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th data-order-key="no">编号</th>
                                <th>助记码</th>
                                <th>名称</th>
                                <th>单位</th>
                                <th>定价</th>
                                <th class="order pagination-reorder" data-order-key="sale">售价</th>
                                <th>状态</th>
                                <th>点赞人数</th>
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
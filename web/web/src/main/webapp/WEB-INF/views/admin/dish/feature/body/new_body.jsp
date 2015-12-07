<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">菜品管理</a></li>
            <li class="active">本店特色</li>
        </ol>
        <h2>菜品管理-本店特色</h2>
    </div>
    <div class="col-sm-12">
        <div class="alert alert-danger J_tip" role="alert">保存失败！</div>
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>添加</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_selectForm" action="" method="">
                    <input type="hidden" name="tagId" value="001">
                    <div class="form-group">
                        <label class="control-label col-sm-2">选择菜品分类</label>
                        <div class="col-sm-10 checkbox block J_labels">
                            <label>
                                <input class="J_selectAll" type="checkbox" name="tagIdList" value="-1">全部
                            </label>
                            <c:forEach var="tag" items="${tagList}">
                                <label>
                                    <input class="J_dishClassify" type="checkbox" name="tagIdList" value="${tag.id}">${tag.name}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                </form>
                <form class="form-horizontal J_addForm" action="" method="">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>助记码</th>
                                <th>菜品名称</th>
                                <th>单位</th>
                                <th>定价</th>
                                <th>售价</th>
                                <th>状态</th>
                                <th>本店特色</th>
                            </tr>
                            </thead>
                            <tbody id="dataRender">
                            </tbody>
                        </table>
                    </div>
                </form>
                <div class="col-sm-6 col-sm-offset-4">
                    <div class="btn-toolbar">
                        <button class="btn btn-primary J_save" type="submit"><i class="fa fa-save"></i>&nbsp;保存</button>
                        <button class="btn btn-default" type="button" onclick="window.history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
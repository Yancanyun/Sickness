<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">存放点管理</li>
        </ol>
        <h2>存放点管理</h2>
    </div>
    <div class="col-sm-12">
        <form class="form-horizontal J_submitForm" action="${website}admin/storage/depot/update" method="post">

            <c:if test="${!empty msg}">
                <div class="alert alert-danger" role="alert">${msg}</div>
            </c:if>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>编辑</h4>
                </div>
                <div class="panel-body">
                    <input type="hidden" name="id" value="${storageDepot.id}">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="w180" data-valid-tip="请输入长度为1~15个字符的存放点名称|输入有误，请重新输入" data-valid-rule="notNull&length(0,16)" name="name" value="${storageDepot.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">简介</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" data-valid-tip="请输入长度为0~20个字符的简介|输入有误，请重新输入" data-valid-rule="isNull|length(0,21)" name="introduction" value="${storageDepot.introduction}"></textarea>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <div class="btn-toolbar">
                                <button class="btn btn-primary J_submitBtn" type="submit" data-btn-type="loading" data-btn-loading-text="正在保存，请稍后">
                                    <i class="fa fa-save"></i>&nbsp;保存
                                </button>
                                <button class="btn btn-default" type="reset" onclick="window.history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
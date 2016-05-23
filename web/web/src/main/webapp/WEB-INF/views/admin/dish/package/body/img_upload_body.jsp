<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="">菜品管理</a></li>
            <li class="active">套餐管理</li>
        </ol>
        <h2>菜品管理-套餐管理-添加套餐图片</h2>
    </div>
    <!--<form class="J_operForm" action="" method="">-->
    <input class="J_id" type="hidden" name="id" value="${dishId}">
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>套餐大图</h4>
            </div>
            <div class="panel-body">
                <p class="padding-left-15">暂无,请您添加上传!</p>
                <div class="col-sm-12 margin-top-15">
                    <div class="clearfix uploader">
                        <div id="dndArea1" class="placeholder">
                            <div id="fileList1" class="uploader-list clearfix"></div>
                        </div>
                        <div id="filePicker1" class="pull-left">选择图片</div>
                        <button id="ctlBtn1" class="btn btn-default">开始上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>套餐小图</h4>
            </div>
            <div class="panel-body">
                <p class="padding-left-15">暂无,请您添加上传!</p>
                <div class="col-sm-12 margin-top-15">
                    <div class="clearfix uploader">
                        <div id="dndArea2" class="placeholder">
                            <div id="fileList2" class="uploader-list clearfix"></div>
                        </div>
                        <div id="filePicker2" class="pull-left">选择图片</div>
                        <button id="ctlBtn2" class="btn btn-default">开始上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-sm-offset-3 margin-bottom-20">
        <div class="btn-toolbar">
            <button class="btn-default btn" type="button" onclick="location='${website}admin/dish/package'"><i class="fa fa-undo"></i>&nbsp;返回</button>
        </div>
    </div>
</div>

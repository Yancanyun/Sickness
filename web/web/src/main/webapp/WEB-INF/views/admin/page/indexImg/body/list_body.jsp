<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">基本信息管理</a></li>
            <li class="active">顾客点餐平台首页管理</li>
        </ol>
        <h2>基本信息管理-添加顾客点餐平台首页图片</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>当前图片</h4>
            </div>
            <div class="panel-body">
                <div class="col-sm-4 col-sm-offset-4">
                    <img class="img-responsive" src="/resources/img/admin/base-info/index.jpg" alt="顾客点餐平台首页">
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>修改图片</h4>
            </div>
            <div class="panel-body">
                <h4>备选图片</h4>
                <hr>
                <div class="col-sm-12 margin-bottom-15">
                    <div class="row">
                        <div class="col-sm-3 text-center">
                            <img class="img-responsive" src="/resources/img/admin/base-info/index.jpg" alt="顾客点餐平台首页备选图片">
                            <h5 class="margin-top-15">当前顾客点餐平台首页</h5>
                        </div>
                        <div class="col-sm-3 text-center">
                            <img class="img-responsive" src="/resources/img/admin/base-info/index1.jpg" alt="顾客点餐平台首页备选图片">
                            <a class="margin-top-15 btn btn-success" href="#"><i class="fa fa-check"></i>&nbsp;点击设置为首页</a>
                        </div>
                        <div class="col-sm-3 text-center">
                            <img class="img-responsive" src="/resources/img/admin/base-info/index3.jpg" alt="顾客点餐平台首页备选图片">
                            <a class="margin-top-15 btn btn-success" href="#"><i class="fa fa-check"></i>&nbsp;点击设置为首页</a>
                        </div>
                    </div>
                </div>
                <h4>上传图片</h4>
                <hr>
                <div class="col-sm-12 margin-top-15">
                    <div id="uploader" class="clearfix">
                        <div id="dndArea" class="placeholder">
                            <div id="fileList" class="uploader-list clearfix"></div>
                        </div>
                        <div id="filePicker" class="pull-left">选择图片</div>
                        <button id="ctlBtn" class="btn btn-default">开始上传</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- <div id="uploader" class="clearfix">
        <div id="dndArea" class="placeholder">
            <div id="fileList" class="uploader-list clearfix"></div>
        </div>
        <div id="filePicker" class="pull-left">选择图片</div>
        <button id="ctlBtn" class="btn btn-default">开始上传</button>
    </div> -->
</div>

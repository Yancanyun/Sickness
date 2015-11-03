<%--
  User: chenyuting
  Time: 2015/11/3 09:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">用户信息管理</a></li>
            <li class="active">会员管理</li>
        </ol>
        <h2>会员管理-查看详情</h2>
    </div>
    <div class="col-sm-12">
        <form class="form-horizontal J_addForm" action="" method="">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>详情</h4>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>姓名</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${vipInfoDto.name}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">性别</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${vipInfoDto.sex}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">生日</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${vipInfoDto.birthday}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>手机号</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${vipInfoDto.phone}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">卡号</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">0000000001</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">余额</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">50.00</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">qq</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${vipInfoDto.qq}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">邮箱</label>
                        <div class="col-sm-6">
                            <p class="form-control-static">${vipInfoDto.email}</p>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-3">
                        <div class="btn-toolbar">
                            <button class="btn-default btn" onclick="window.history.go(-1);" type="button"><i class="fa fa-undo J_back"></i>&nbsp;返回上一页</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <jsp:include page="admin/common/head.jsp"></jsp:include>
    <link rel="stylesheet" href="${staticWebsite}css/admin/login/login.css" />
</head>
<body class="login-background">
<div class="page container clearfix">
    <div class="row">
        <div class="login-heading">
            <h3 class="text-center">点餐系统V2.0</h3>
        </div>
    </div>
    <div class="row">
        <div class="licence-body login-box">
            <div class="title">
                <h4 class="text-center margin-bottom-30">请注册</h4>
            </div>
            <form class="form-horizontal" action="${website}register" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="" class="col-sm-5 label-control-style">系统指纹:</label>
                    <div class="col-sm-7 padding-left-style">
                        <p class="form-control-static">${sysId}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-5 label-control-style">注册文件：</label>
                    <input id="pandaworkMultipartFile" class="col-sm-7" name="pandaworkMultipartFile" type="file" style="padding-top: 5px;margin-left: -15px;"/>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-5 label-control-style">管理员密码：</label>
                    <input id="password" class="col-sm-7 J_adminPwd" name="password" type="password" placeholder="请输入管理员密码" data-valid-tip="请输入密码|密码不能为空，请重新输入" data-valid-rule="isPassword" value=""/>
                </div>
                <div class="form-group">
                    <label class="col-sm-5 label-control-style">当前注册状态：</label>
                    <div class="col-sm-7">
                        <p class="form-control-static padding-right15" style="color: red">${emsg}</p>
                    </div>
                </div>
                <div class="form-group">
                    <div class="btn-toolbar">
                        <button class="J_submitBtn btn btn-primary btn-block" type="submit" data-btn-type="loading" data-btn-loading-text="正在登录，请稍后..." >提交</button>
                    </div>
                </div>
                <p class="licence-tip-info">
                    <a class="first-link" href="${website}admin">返回后台登录界面</a>
                    <a href="${website}cook">返回后厨登录界面</a>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    KISSY.ready(function (S) {
        S.use('page/licence', function (S) {
            PW.page.Licence();
        });
    });
</script>
</html>

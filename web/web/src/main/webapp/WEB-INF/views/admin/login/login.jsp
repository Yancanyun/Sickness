<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
    <%@include file="../common/head.jsp" %>
    <link rel="stylesheet" href="${staticWebsite}css/admin/login/login.css" />
</head>
<body class="login-background">
<div class="page container clearfix">
    <div class="row">
        <div class="login-heading">
            <h3 class="text-center">点餐系统V2.0</h3>
        </div>
    </div>
    <form class="J_loginForm" method="post" action="${website}admin/login">
        <div class="row">
            <div class="login-body login-box">
                <div class="title">
                    <h4 class="text-center margin-top-25 margin-bottom-30">欢迎登录
                    </h4>
                </div>
                <div class="form-group">
                    <div class="input-group">
								<span class="input-group-addon">
									<span class="fa fa-user"></span>
								</span>
                        <input class="J_username form-control" type="text" data-valid-tip=" 请输入用户名|用户名不能为空，请重新输入" data-valid-rule="notNull" placeholder="请填写登录用户名" name="loginName" value="">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
								<span class="input-group-addon">
									<span class="fa fa-lock"></span>
								</span>
                        <input class="J_pwd form-control" type="password" data-valid-tip="请输入密码|密码不能为空，请重新输入" data-valid-rule="isPassword" placeholder="请填写登录密码" name="password" value="">
                    </div>
                </div>
                <div class="checkbox pull-right margin-top-0 margin-bottom-15">
                    <label>
                        <input class="J_remember height-18" type="checkbox" value="0" name="isRememberMe">
                        记住我的登录信息
                    </label>
                </div>
                <button class="J_submitBtn btn btn-primary btn-block" type="submit" data-btn-type="loading" data-btn-loading-text="正在登录，请稍后..." >登录</button>
            </div>
        </div>
        <div class="row">
            <div class="login-footer clearfix">
                <div class="tip-info pull-left">
                    <b>出现错误？</b>
                    <p>请联系管理员</p>
                </div>
                <div class="pull-right">
                    <button class="btn btn-default reset-btn J_resetBtn" type="reset">重置
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/login/login',function(S){
            PW.page.Login.Index();
        });
    });
</script>
</body>
</html>

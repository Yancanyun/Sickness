<%--
  Created by IntelliJ IDEA.
  User: hual
  Date: 13-12-24
  Time: 下午3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <title>点餐系统后厨登录</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${staticWebsite}css/cook/common/bootstrap.css">
  <link rel="stylesheet" href="${staticWebsite}css/cook/common/bootstrap-theme.min.css">
  <link rel="stylesheet" href="${staticWebsite}css/cook/pages/style.css">
  <script src="${staticWebsite}js/cook/site-config.js"></script>
  <script src="http://pui.pandawork.net/resources/js/base/lib/kissy/seed.js"></script>

  <!--[if lt IE 9]>
  <script src="${staticWebsite}js/cook/app/page/common/html5.js"></script>
  <script src="${staticWebsite}js/cook/app/page/common/respond.min.js"></script>
  <![endif]-->
</head>

<body>
<div class="container">
  <section class="loginBox row-fluid">
    <section class="col-sm-7 left">
      <h2>后厨调度系统登录</h2>
      <form id="J_loginForm" role="form" method="post" action="${website}cook">
        <div class="form-group">
          <input name="username" type="text" data-valid-rule="notNull" class="form-control input-sm" id="J_userName" placeholder="用户名">
        </div>
        <div class="form-group">
          <input name="password" type="password" data-valid-rule="isPassword" class="form-control input-sm" id="J_password" placeholder="密码">
        </div>
        <button type="submit" class="btn btn-default">登录</button>
      </form>
    </section>
    <section class="col-sm-5 right">
      <h2>出现错误？</h2>
      <section>
        <p>如果您发现登录异常，或者不能登录，请联系系统管理员。</p>
      </section>
    </section>
  </section>
  <!-- /loginBox -->
</div><!-- /container -->
<script>
  KISSY.use('page/login', function(S){
    S.ready(function(){
      PW.page.login();
    });
  })
</script>
</body>
</html>
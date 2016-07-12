<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>上菜扫单</title>
  <link rel="stylesheet" href="${staticWebsite}css/cook/common/bootstrap.min.css">
  <link rel="stylesheet" href="${staticWebsite}css/cook/common/bootstrap-theme.min.css">
  <link rel="stylesheet" href="${staticWebsite}css/cook/pages/style.css">
  <script src="http://pui.pandawork.net/resources/js/base/lib/thirdparty/jquery-min.js"></script>
  <script src="${staticWebsite}tool/bootstrap/js/bootstrap.min.js"></script>
  <script src="${website}resources/js/cook/site-config.js"></script>
  <script src="http://pui.pandawork.net/resources/js/base/lib/kissy/seed.js"></script>

  <!--[if lt IE 9]>
  <script src="${staticWebsite}js/cook/app/page/common/respond.min.js"></script>
  <![endif]-->
</head>
<body class="scan-page">
<div class="scan-container">
  <!--{扫描表单 Start}-->
  <form id="J_scanForm" class="form-inline scan-form" role="form">
    <div class="form-group">
      <label class="sr-only" for="exampleInputEmail2">菜品编号：</label>
      <input id="J_barCodeField" type="text" class="form-control" id="exampleInputEmail2" placeholder="菜品编号扫入"></div>
    <button type="submit" class="btn btn-default">扫描</button>
  </form>
  <!--{扫描表单 End}-->

  <!--{扫描结果 Start}-->
  <div id="J_scanResult" class="scan-result-container alert alert-info">
    <strong>扫描结果：</strong>
    <span id="J_resultReport">成功</span>
  </div>
  <!--{扫描结果 End}-->

  <!--{扫描记录 Start}-->
  <div class="scaned-dish-container">
    <table class="table table-striped">
      <thead>
      <tr>
        <th>菜品编号</th>
        <th>时间</th>
        <th>结果</th>
      </tr>
      </thead>
      <tbody id="J_scanedDishData">
      <!--  <tr>
          <td>159</td>
          <td>2013-04-13 12:34:55</td>
          <td>成功</td>
      </tr> -->
      </tbody>
    </table>
  </div>
  <!--{扫描记录 End}-->
</div>
<script>
  KISSY.use('page/scan-dish', function(S){
    S.ready(function(){
      PW.page.ScanDish();
    });
  })
</script>
</body>
</html>
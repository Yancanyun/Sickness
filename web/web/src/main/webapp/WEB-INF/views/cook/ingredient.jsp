<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>原配料标缺</title>
  <link rel="stylesheet" href="${staticWebsite}css/cook/common/bootstrap.min.css">
  <link rel="stylesheet" href="${staticWebsite}css/cook/common/bootstrap-theme.min.css">
  <link rel="stylesheet" href="${staticWebsite}css/cook/pages/style.css">
  <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
  <script src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>
  <script src="${staticWebsite}js/cook/site-config.js"></script>
  <script src="${puiWebsite}resources/js/base/lib/kissy/seed.js"></script>
  <script id="J_searchListTpl" type="text/template">
    {@each ingredientList as ing, index}
    <a data-ing-id="&{ing.id}" href="javascript:;" class="list-group-item ing-item">
      <div class="row">
        <div class="col-xs-10">
          <span class="ing-name">&{ing.name}</span>
          <span>(&{ing.id})</span>
        </div>
        <div class="col-xs-2">
                    <span class="badge">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </span>
        </div>
      </div>
    </a>
    {@/each}
  </script>
  <script id="J_ingWarnListTpl" type="text/template">
    <a data-ing-id="&{id}" href="javascript:;" class="list-group-item">
      <div class="row">
        <div class="col-xs-2">
                    <span class="badge">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                    </span>
        </div>
        <div class="col-xs-10">
          <span class="ing-name">&{name}</span>
          <span>(&{id})</span>
        </div>
      </div>
    </a>
  </script>

  <!--[if lt IE 9]>
  <script src="${staticWebsite}js/cook/app/page/common/respond.min.js"></script>
  <![endif]-->
</head>
<body class="ing-warn-page">
<div class="container-fluid">
  <div class="row ing-warn-container">
    <div class="col-xs-5 search-panel">
      <!--{原材料搜索 Start}-->
      <form id="J_ingSearch" class="form-inline ing-search" role="form">
        <div class="form-group">
          <label class="sr-only" for="ingNameField">菜品编号：</label>
          <input id="J_ingNameField" name="keyword" type="text" class="form-control" id="ingNameField" placeholder="输入原材料名">
        </div>
        <button type="submit" class="btn btn-default">搜索</button>
      </form>
      <!--{原材料搜索 End}-->

      <!--{搜索结果表格 Start}-->
      <div class="search-result">
        <h2>原材料搜索结果：</h2>
        <div class="list-group" id="J_ingSearchList">
          <!-- note: rendered by juicer -->
        </div>
      </div>
      <!--{搜索结果表格 End}-->
    </div>
    <div class="col-xs-2"></div>
    <div class="col-xs-5 warn-panel">
      <h2>已标缺配料(注意，双击恢复):</h2>
      <div  class="list-group" id="J_warnedIngList">

        <c:forEach items="${ingredientList}" var="ingredient">
          <a data-ing-id="${ingredient.id}" href="javascript:;" class="list-group-item ing-item">
            <div class="row">
              <div class="col-xs-2">
                                <span class="badge">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                </span>
              </div>
              <div class="col-xs-10">
                <span class="ing-name">${ingredient.name}</span>
                <span class="ing-id">${ingredient.id}</span>
              </div>
            </div>
          </a>
        </c:forEach>
      </div>
    </div>
  </div>
</div>
<script>
  KISSY.use('page/ingredient', function(S){
    S.ready(function(){
      PW.page.Ingredient();
    });
  })
</script>
</body>
</html>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>后厨调度系统</title>
  <link rel="stylesheet" href="${staticWebsite}css/cook/common/bootstrap.css">
  <link rel="stylesheet" href="${staticWebsite}css/cook/common/bootstrap-theme.min.css">
  <link rel="stylesheet" href="${staticWebsite}css/cook/pages/style.css">
  <%--<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>--%>
  <%--<script src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>--%>
  <script src="${website}resources/js/cook/site-config.js"></script>
  <script src="http://pui.pandawork.net/resources/js/base/lib/kissy/seed.js"></script>
  <script type="text/template" id="J_tpl">
    <div class="grid table-container" data-version="&{version}">
      <div class="table-inner">
        <div class="table-brief-info">
          <span class="table-name">&{name}</span>
        </div>
        <div class="panel-body table-info-container">
          {@each orders as order, index}
          <!--{单个订单 Start}-->
          <div class="order-container">
            <a href="javascript:;" class="badge time" data-order-time="&{order.time}">&{order.time}</a>
            <h5 class="order-brief-info">
              <span>订单&{order.id}</span>
            </h5>
            <!--{订单备注 Start}-->
            <h6 class="order-remark">
              <b class="label label-warning">备注:</b>
              <span>&{order.remark}</span>
            </h6>
            <!--{订单备注 End}-->
            <!--{订单详情 Start}-->
            <table class="table">
              <thead>
              <tr>
                <th width="50">状态</th>
                <th width="50">类型</th>
                <th>菜名</th>
                <th width="80">数量</th>
                <th width="50">上菜</th>
                <th width="80">操作</th>
              </tr>
              </thead>
              <tbody>
              {@each order.orderDishes as orderDish}
              <tr class="
                                {@if orderDish.state == 1}ordered{@else}cooking{@/if}
                                {@if orderDish.isChange == 1}changed{@/if}
                                {@if orderDish.isCall == 1}faster{@/if}
                                " data-order-dish-id="&{orderDish.id}">
                <td class="dish-state"></td>
                <td class="dish-tag-name">&{orderDish.bigTagName}</td>
                <td class="dish-name">&{orderDish.name}</td>
                <td>
                  <span class="dish-number">&{orderDish.num}</span>
                  <span class="dish-unit-name">&{orderDish.unitName}&{orderDish.color}</span>
                </td>
                <td class="dish-serve-type">
                  {@if orderDish.serveType == 1}
                  叫起
                  {@else}
                  即起
                  {@/if}
                </td>
                <td class="btn-wrapper" {@if orderDish.state != 1 }style="display: none;"{@/if}>
                <button type="button" class="J_doPrint btn btn-default btn-sm">
                  <span class="glyphicon glyphicon-print"></span>
                  {@if orderDish.state != 1 }
                  重打
                  {@else}
                  打印
                  {@/if}
                </button>
                </td>
                <td class="printed" {@if orderDish.state == 1 }style="display: none;"{@/if}>
                已打印
                </td>

              </tr>
              {@/each}
              </tbody>
            </table>
            <!--{订单详情 End}-->
          </div>
          <!--{单个订单 End}-->
          {@/each}
        </div>
      </div>
    </div>
  </script>

  <!--[if lt IE 9]>
  <script src="${staticWebsite}js/cook/app/page/common/respond.min.js"></script>
  <![endif]-->
</head>
<body>
<!--{顶部导航 Start}-->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#">后厨调度系统</a>
  </div>

  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <!--{搜索表单 Start}-->
    <%--<form class="navbar-form navbar-left" role="search">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="菜名/原材料/类型/速记码">
        </div>
        <button type="submit" class="btn btn-default">搜索菜品</button>
    </form>--%>
    <!--{搜索表单 End}-->
    <a href="${website}cook/ingredient" id="J_ingredientWarnModal" class="btn btn-warning ing-warn">原材料标缺</a>
    <a href="${website}cook/ordermanage/wipe" id="J_openScanModal" class="btn btn-info scan-dish">上菜扫单</a>
    <!--{附加菜单 Start}-->
    <ul class="nav navbar-nav navbar-right additional-fns">
      <li class="dropdown">
        <div class="btn-group">
          <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
            当前登录：${web_user_login_name}
            <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${website}cook/logout">退出登录</a></li>
          </ul>
        </div>
      </li>
    </ul>
    <!--{附加菜单 End}-->
  </div>
</div>
<!--{顶部导航 End}-->

<!--{瀑布流 Start}-->
<div id="J_tablesContainer" class="waterfall-container">

</div>
<!--{瀑布流 End}-->

<script>
  KISSY.use('page/common/waterfall, page/kitchen', function(S){
    S.ready(function(){
      PW.page.Kitchen();
    });
  })
</script>
</body>
</html>

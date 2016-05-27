<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- <div class="row">
<div class="col-sm-12"> -->
<nav class="navbar navbar-default navbar-static-top admin-nav J_admin_nav">
    <a class="left-toggle pull-left" href="javascript:;">
        <i class="fa fa-bars"></i>
    </a>
    <a class="pull-left logo-text menu-list-logo" href="${website}admin"><i class="fa fa-cutlery"></i>&nbsp;餐饮后台管理</a>
    <a class="pull-right header-menu" href="javascript:;"><i class="fa fa-bars"></i>&nbsp;</a>
    <ul class="nav navbar-nav">
        <li
                <c:if test="${PWModule eq 'Admin:BasicInfo'}">class="active"</c:if> >
            <a class="menu-list J_layer_1_menu" data-menu-id="1" href="javascript:;">基本信息管理</a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a class="J_menu" href="javascript:;">后台欢迎页</a></li>
                <li><a class="J_menu" href="${website}admin/keywords">搜索风向标</a></li>
                <li><a class="J_menu" href="${website}admin/index/img">点餐平台首页</a></li>
                <li><a class="J_menu" href="${website}admin/printer">打印机管理</a></li>
                <li><a class="J_menu" href="javascript:;">外卖参数设置</a></li>
                <li><a class="J_menu" href="javascript:;">全局设置</a></li>
            </ul>
        </li>
        <li>
        <li <c:if test="${PWModule eq 'Admin:Restaurant:Table'}">class="active"</c:if> >
            <a class="menu-list J_layer_1_menu" data-menu-id="2" href="javascript:;">饭店管理</a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a class="J_menu" href="${website}admin/restaurant/area">餐台区域管理</a></li>
                <li><a class="J_menu" href="${website}admin/restaurant/call/waiter">呼叫服务类型管理</a></li>
                <li><a class="J_menu" href="${website}admin/restaurant/table">餐台管理</a></li>
                <li><a class="J_menu" href="${website}admin/restaurant/qrcode">餐台二维码</a></li>
                <li><a class="J_menu" href="${website}admin/restaurant/meal/period">餐段管理</a></li>
                <li><a class="J_menu" href="${website}admin/restaurant/remark">备注管理</a></li>
            </ul>
        </li>
        <li <c:if test="${PWModule eq 'Admin:DishManagement'}">class="active" </c:if> >
            <a class="menu-list J_layer_1_menu" data-menu-id="3" href="javascript:;">菜品管理</a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a class="J_menu" href="${website}admin/dish/unit">菜品单位管理</a></li>
                <li><a class="J_menu" href="${website}admin/dish/tag">菜品分类管理</a></li>
                <li><a class="J_menu" href="${website}admin/dish/taste">菜品口味管理</a></li>
                <li><a class="J_menu" href="${website}admin/dish/cost/card">菜品成本卡管理</a></li>
                <li><a class="J_menu" href="${website}admin/dish">菜品管理</a></li>
                <li><a class="J_menu" href="${website}admin/dish/package">套餐管理</a></li>
                <li><a class="J_menu" href="${website}admin/dish/feature">本店特色</a></li>
                <li><a class="J_menu" href="${website}admin/dish/today/cheap">今日特价</a></li>
                <li><a class="J_menu" href="${website}admin/dish/sale/ranking">销量排行</a></li>
            </ul>
        </li>
        <li>
            <a class="menu-list J_layer_1_menu" data-menu-id="5" href="javascript:;">用户信息管理</a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a class="J_menu" href="${website}admin/party/group/employee/">员工管理</a></li>
                <li><a class="J_menu" href="${website}admin/party/group/vip">会员管理</a></li>
                <li><a class="J_menu" href="${website}admin/vip/price/plan/list">会员价方案管理</a></li>
                <li><a class="J_menu" href="${website}admin/vip/recharge/plan/list">会员充值方案管理</a></li>
                <li><a class="J_menu" href="${website}admin/vip/grade">会员等级管理</a></li>
                <li><a class="J_menu" href="${website}admin/vip/multiple/integral/plan">多倍积分方案管理</a></li>
                <li><a class="J_menu" href="${website}admin/vip/account/list">会员账户信息管理</a></li>
                <li><a class="J_menu" href="${website}admin/vip/card">会员卡管理</a></li>
            </ul>
        </li>
        <li <c:if test="${PWModule eq 'Admin:Storage'}">class="active"</c:if> >
            <a class="menu-list J_layer_1_menu" data-menu-id="6" href="javascript:;">库存管理</a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a class="J_menu" href="${website}admin/storage/supplier">供货商管理</a></li>
                <li><a class="J_menu" href="${website}admin/storage/tag">库存分类管理</a></li>
                <li><a class="J_menu" href="${website}admin/storage/ingredient">库存原配料管理</a></li>
                <li><a class="J_menu" href="${website}admin/storage/item">库存物品管理</a></li>
                <li><a class="J_menu" href="${website}admin/storage/report">库存单据管理</a></li>
                <li><a class="J_menu" href="${website}admin/storage/settlement/check">库存盘点</a></li>
                <li><a class="J_menu" href="${website}admin/storage/settlement/supplier">结算中心</a></li>
                <li><a class="J_menu" href="javascript:;">库存更新管理</a></li>
                <li><a class="J_menu" href="javascript:;">预警管理</a></li>
                <li><a class="J_menu" href="${website}admin/storage/depot">存放点管理</a></li>
            </ul>
        </li>
        <li>
            <a class="menu-list J_layer_1_menu" data-menu-id="7" href="javascript:;">营收统计</a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a class="J_menu" href="javascript:;">账单统计</a></li>
                <li><a class="J_menu" href="javascript:;">会员充值统计</a></li>
            </ul>
        </li>
        <li>
            <a class="menu-list J_layer_1_menu" data-menu-id="8" href="javascript:;">营业分析</a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a class="J_menu" href="javascript:;">菜品销售排行</a></li>
                <li><a class="J_menu" href="javascript:;">菜品销售趋势排行</a></li>
                <li><a class="J_menu" href="javascript:;">菜品大类销售排行</a></li>
                <li><a class="J_menu" href="javascript:;">餐台使用排行</a></li>
                <li><a class="J_menu" href="javascript:;">餐台区域使用排行</a></li>
            </ul>
        </li>
        <li <c:if test="${PWModule eq 'Admin:SAdmin'}">class="active"</c:if>>
            <a class="menu-list J_layer_1_menu" data-menu-id="9" href="javascript:;">超级管理</a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a class="J_menu" href="${website}admin/party/security/permission">权限管理</a></li>
                <li><a class="J_menu" href="${website}admin/party/security/group">安全组管理</a></li>
                <li><a class="J_menu" href="javascript:;">菜品大类销售排行</a></li>
                <li><a class="J_menu" href="javascript:;">餐台使用排行</a></li>
                <li><a class="J_menu" href="javascript:;">餐台区域使用排行</a></li>
            </ul>
        </li>
    </ul>
    <ul class="nav navbar-nav hello-administrator pull-right">
        <li>
            <a class="menu-list J_adminInfo" href="javascript:;">你好，管理员！<span class="caret"></span></a>
            <ul class="dropdown-menu arrow" aria-labelledby="dLabel">
                <li><a href="javascript:;"><i class="fa fa-user"></i>&nbsp;修改个人信息</a></li>
                <li class="divider"></li>
                <li><a href="${website}admin/logout"><i class="fa fa-sign-out"></i>&nbsp;退出登录</a></li>
            </ul>
        </li>
        <li>
            <img class="pull-right img-responsive administrator" src="${staticWebsite}img/admin/admin.png" alt="管理员图片"
                 title="管理员图片">
        </li>
    </ul>
</nav>
<!-- </div>
</div> -->


<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('module/header, widget/ext', function(S){
            PW.module.header();
        });
    });
</script>

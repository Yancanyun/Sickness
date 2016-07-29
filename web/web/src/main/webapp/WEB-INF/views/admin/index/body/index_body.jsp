<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="col-md-10 col-sm-offset-1">
    <c:if test="${!empty msg}">
        <div class="alert alert-success col-sm-12 J_msg" role="alert">${msg}</div>
    </c:if>
    <h3 class="welcome-head text-center">欢迎使用餐饮后台管理系统</h3>
    <shiro:checkPermission name="Admin:BasicInfo:Keywords">
        <div class="col-md-2 module first-module">
            <a class="text-center" href="${website}admin/keywords">
                <i class="fa fa-flag fa-3x text-center"></i>
                <br>搜索风向标
            </a>
        </div>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:DishManagement">
        <div class="col-md-2 module second-module">
            <a class="text-center" href="${website}admin/dish">
                <i class="fa fa-delicious fa-3x"></i>
                <br>菜品管理
            </a>
        </div>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:Restaurant:Table">
        <div class="col-md-2 module forth-module">
            <a class="text-center" href="${website}admin/restaurant/table" >
                <i class="fa fa-cutlery fa-3x"></i>
                <br>餐台管理
            </a>
        </div>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:DishManagement:TodayCheap">
        <div class="col-md-2 module fifth-module">
            <a class="text-center" href="${website}admin/dish/today/cheap">
                <i class="fa fa-google-wallet fa-3x"></i>
                <br>今日特价
            </a>
        </div>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:DishManagement:Feature">
        <div class="col-md-2 module sixth-module">
            <a class="text-center" href="${website}admin/dish/feature">
                <i class="fa fa-pie-chart fa-3x"></i>
                <br>本店特色
            </a>
        </div>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:Storage:Ingredient">
        <div class="col-md-2 module seventh-module">
            <a class="text-center" href="#">
                <i class="fa fa-cloud fa-3x"></i>
                <br>原配料管理
            </a>
        </div>
    </shiro:checkPermission>
    <div class="col-md-2 module eight-module">
        <a class="text-center" href="#">
            <i class="fa fa-list fa-3x"></i>
            <br>销售排行
        </a>
    </div>
    <div class="col-md-2 module ninth-module">
        <a class="text-center" href="#">
            <i class="fa fa-cogs fa-3x"></i>
            <br>库存管理
        </a>
    </div>
    <div class="col-md-2 module tenth-module">
        <a class="text-center" href="#">
            <i class="fa fa-building fa-3x"></i>
            <br>营收统计
        </a>
    </div>
</div>
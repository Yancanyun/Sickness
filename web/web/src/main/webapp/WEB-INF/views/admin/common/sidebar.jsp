<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="nav nav-pills nav-stacked sidebar pull-left">
    <li id="firstMenu1" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">基本信息管理</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li><a href="#">后台欢迎页</a></li>
            <li <c:if test="${MethodModule eq 'Admin:BasicInfo:Keywords:List'}">class="active"</c:if>>
                <a href="${website}admin/keywords">搜索风向标</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:BasicInfo:IndexImg:List'}">class="active"</c:if>>
                <a href="${website}admin/index/img">点餐平台首页</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:BasicInfo:Printer'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/printer">
                    打印机管理
                    <c:if test="${ExtModule eq 'Admin:BasicInfo:Printer:New'
                                    or ExtModule eq 'Admin:BasicInfo:Printer:Update'}"><i class="fa fa-angle-right angle-right"></i></c:if>
                </a>
                <c:if test="${ExtModule eq 'Admin:BasicInfo:Printer:New'
                                or ExtModule eq 'Admin:BasicInfo:Printer:Update'}">
                    <ul>
                        <li <c:if test="${ExtModule eq 'Admin:BasicInfo:Printer:New'}">class="active" </c:if>>
                            <a href="${website}admin/printer/new">添加打印机</a>
                        </li>
                        <c:if test="${ExtModule eq 'Admin:BasicInfo:Printer:Update'}">
                            <li class="active">
                                <a href="#">编辑打印机</a>
                            </li>
                        </c:if>
                    </ul>
                </c:if>
            </li>
            <li><a href="#">外卖参数设置</a></li>
            <li><a href="#">全局设置</a></li>
        </ul>
    </li>
    <li id="firstMenu2" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">饭店管理</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li <c:if test="${MethodModule eq 'Admin:Restaurant:Area:List'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/restaurant/area">餐台区域管理</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:Restaurant:Table:List'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/restaurant/table">餐台管理</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:Restaurant:QrCode:List'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/restaurant/qrcode">餐台二维码</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:Restaurant:MealPeriod:List'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/restaurant/meal/period">餐段管理</a>
            </li>
            <li><a class="J_menu" href="javascript:;">今日特价</a></li>
            <li><a class="J_menu" href="javascript:;">销售排行</a></li>
            <li><a class="J_menu" href="javascript:;">本店特色</a></li>
        </ul>
    </li>
    <li id="firstMenu3" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">菜品管理</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li <c:if test="${MethodModule eq 'Admin:DishManagement:Unit:List'}">class="active" </c:if>>
                <a class="J_menu" href="${website}admin/dish/unit">菜品单位管理</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:DishManagement:Tag:List'}">class="active" </c:if>>
                <a class="J_menu" href="${website}admin/dish/tag">菜品分类管理</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:DishManagement:Dish'}">class="active"</c:if> >
                <a class="J_menu" href="${website}admin/dish">菜品管理
                    <c:if test="${ExtModule eq 'Admin:DishManagement:Dish:New'
                                or ExtModule eq 'Admin:DishManagement:Dish:Update'}">
                        <i class="fa fa-angle-right angle-right"></i>
                    </c:if>
                </a>
                <c:if test="${ExtModule eq 'Admin:DishManagement:Dish:New'
                                or ExtModule eq 'Admin:DishManagement:Dish:Update'}">
                    <ul>
                        <li <c:if test="${ExtModule eq 'Admin:DishManagement:Dish:New'}">class="active"</c:if> >
                            <a href="${website}admin/dish/new" >添加菜品</a>
                        </li>
                        <c:if test="${ExtModule eq 'Admin:DishManagement:Dish:Update'}">
                            <li class="active"><a href="#">编辑菜品</a></li>
                        </c:if>
                    </ul>
                </c:if>

            </li>
        </ul>
    </li>
    <li id="firstMenu4" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">原配料管理</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li><a class="J_menu" href="javascript:;">原配料单位管理</a></li>
            <li><a class="J_menu" href="javascript:;">原配料管理</a></li>
        </ul>
    </li>
    <li id="firstMenu5" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">用户信息管理</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li <c:if test="${MethodModule eq 'Admin:User:Management:Employee:List'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/party/group/employee/">用户管理</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:User:Management:Vip:VipInfo:List'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/party/group/vip/">会员管理</a>
            </li>
        </ul>
    </li>
    <li id="firstMenu6" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">库存管理</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li <c:if test="${MethodModule eq 'Admin:Storage:Supplier:List'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/storage/supplier">供货商管理</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:Storage:Tag:List'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/storage/tag">库存分类管理</a>
            </li>
            <li <c:if test="${MethodModule eq 'Admin:Storage:Item'}">class="active"</c:if>>
                <a class="J_menu" href="${website}admin/storage/item">
                    库存物品管理
                    <c:if test="${ExtModule eq 'Admin:Storage:Item:New'
                                    or ExtModule eq 'Admin:Storage:Item:Update'
                                    or ExtModule eq 'Admin:Storage:Item:UnitConversion:List'}">
                        <i class="fa fa-angle-right angle-right"></i>
                    </c:if>
                </a>
                <c:if test="${ExtModule eq 'Admin:Storage:Item:New'
                                or ExtModule eq 'Admin:Storage:Item:Update'
                                or ExtModule eq 'Admin:Storage:Item:UnitConversion:List'}">
                    <ul>
                        <li <c:if test="${ExtModule eq 'Admin:Storage:Item:New'}">class="active" </c:if>>
                            <a href="${website}admin/storage/item/new">添加库存物品</a>
                        </li>
                        <c:if test="${ExtModule eq 'Admin:Storage:Item:Update'}">
                            <li class="active">
                                <a href="#">编辑库存物品</a>
                            </li>
                        </c:if>
                        <c:if test="${ExtModule eq 'Admin:Storage:Item:UnitConversion:List'}">
                            <li class="active">
                                <a href="${website}admin/storage/item/unit/conversion/list">换算比例</a>
                            </li>
                        </c:if>
                    </ul>
                </c:if>
            </li>
            <li><a class="J_menu" href="javascript:;">库存管理</a></li>
            <li><a class="J_menu" href="javascript:;">库存更新管理</a></li>
            <li><a class="J_menu" href="javascript:;">预警管理</a></li>
        </ul>
    </li>
    <li id="firstMenu7" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">营收统计</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li><a class="J_menu" href="javascript:;">账单统计</a></li>
            <li><a class="J_menu" href="javascript:;">会员充值统计</a></li>
        </ul>
    </li>
    <li id="firstMenu8" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">营业分析</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li><a class="J_menu" href="javascript:;">菜品销售排行</a></li>
            <li><a class="J_menu" href="javascript:;">菜品销售趋势排行</a></li>
            <li><a class="J_menu" href="javascript:;">菜品大类销售排行</a></li>
            <li><a class="J_menu" href="javascript:;">餐台使用排行</a></li>
            <li><a class="J_menu" href="javascript:;">餐台区域使用排行</a></li>
        </ul>
    </li>
    <li id="firstMenu9" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">超级管理</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li <c:if test="${MethodModule eq 'Admin:SAdmin:Party:Security'}">class="active"</c:if> >
                <a class="J_menu" href="javascript:;">权限管理<i class="fa fa-angle-right angle-right"></i></a>
                <ul <c:if test="${MethodModule != 'Admin:SAdmin:Party:Security'}">class="hidden"</c:if>>
                    <li
                            <c:if test="${ExtModule eq 'Admin:SAdmin:Party:Security:Permission:List'}">class="active"</c:if>>
                        <a href="${website}admin/party/security/permission">基本权限管理</a>
                    </li>
                    <li <c:if test="${ExtModule eq 'Admin:SAdmin:Party:Security:Group:List'}">class="active"</c:if>>
                        <a href="${website}admin/party/security/group">安全组管理</a>
                    </li>
                    <li><a href="#">安全组权限管理</a></li>
                </ul>
            </li>
            <li><a class="J_menu" href="javascript:;">菜品销售趋势排行</a></li>
            <li><a class="J_menu" href="javascript:;">菜品大类销售排行</a></li>
            <li><a class="J_menu" href="javascript:;">餐台使用排行</a></li>
            <li><a class="J_menu" href="javascript:;">餐台区域使用排行</a></li>
        </ul>
    </li>
</ul>


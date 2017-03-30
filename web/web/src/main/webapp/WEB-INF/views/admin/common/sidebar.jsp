<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.pandawork.net/tags" prefix="shiro"%>

<ul class="nav nav-pills nav-stacked sidebar pull-left">

    <shiro:checkPermission name="Admin:BasicInfo">
    <li id="firstMenu1" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">基本信息管理</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <li><a href="${website}admin">后台欢迎页</a></li>
                <shiro:checkPermission name="Admin:BasicInfo:Keywords:List">
                    <li <c:if test="${MethodModule eq 'Admin:BasicInfo:Keywords:List'}">class="active"</c:if>>
                        <a href="${website}admin/keywords">搜索风向标</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:BasicInfo:IndexImg:List">
                    <li <c:if test="${MethodModule eq 'Admin:BasicInfo:IndexImg:List'}">class="active"</c:if>>
                        <a href="${website}admin/index/img">点餐平台首页</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:BasicInfo:Printer">
                    <li <c:if test="${MethodModule eq 'Admin:BasicInfo:Printer'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/printer">
                            打印机管理
                            <c:if test="${ExtModule eq 'Admin:BasicInfo:Printer:New'
                                    or ExtModule eq 'Admin:BasicInfo:Printer:Update'}"><i class="fa fa-angle-right angle-right"></i></c:if>
                        </a>
                        <c:if test="${ExtModule eq 'Admin:BasicInfo:Printer:New'
                                or ExtModule eq 'Admin:BasicInfo:Printer:Update'}">
                            <ul>
                                <shiro:checkPermission name="Admin:BasicInfo:Printer:New">
                                    <li <c:if test="${ExtModule eq 'Admin:BasicInfo:Printer:New'}">class="active" </c:if>>
                                        <a href="${website}admin/printer/new">添加打印机</a>
                                    </li>
                                </shiro:checkPermission>
                                <c:if test="${ExtModule eq 'Admin:BasicInfo:Printer:Update'}">
                                    <li class="active">
                                        <a href="#">编辑打印机</a>
                                    </li>
                                </c:if>
                            </ul>
                        </c:if>
                    </li>

                    <li><a href="#">外卖参数设置</a></li>
                    <shiro:checkPermission name="Admin:Constant:List">
                        <li <c:if test="${MethodModule eq 'Admin:Constant:List'}">class="active"</c:if>>
                            <a href="${website}admin/constant">全局设置</a>
                        </li>
                    </shiro:checkPermission>
                </shiro:checkPermission>
        </ul>
    </li>
    </shiro:checkPermission>

    <shiro:checkPermission name="Admin:Restaurant">
        <li id="firstMenu2" class="active hidden">
            <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">饭店管理</span><i
                    class="fa fa-angle-right angle-right"></i></a>
            <ul>
                <shiro:checkPermission name="Admin:Restaurant:Area:List">
                    <li <c:if test="${MethodModule eq 'Admin:Restaurant:Area:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/restaurant/area">餐台区域管理</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Restaurant:Table:List">
                    <li <c:if test="${MethodModule eq 'Admin:Restaurant:Table:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/restaurant/table">餐台管理</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Restaurant:QrCode:List">
                    <li <c:if test="${MethodModule eq 'Admin:Restaurant:QrCode:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/restaurant/qrcode">餐台二维码</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Restaurant:MealPeriod:List">
                    <li <c:if test="${MethodModule eq 'Admin:Restaurant:MealPeriod:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/restaurant/meal/period">餐段管理</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Restaurant:Remark:List">
                    <li <c:if test="${MethodModule eq 'Admin:Restaurant:Remark:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/restaurant/remark">备注管理</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Restaurant:CallWaiter:List">
                    <li <c:if test="${MethodModule eq 'Admin:Restaurant:CallWaiter:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/restaurant/call/waiter">呼叫服务类型管理</a>
                    </li>
                </shiro:checkPermission>
                <li><a class="J_menu" href="javascript:;">销售排行</a></li>
                <li><a class="J_menu" href="javascript:;">本店特色</a></li>
            </ul>
        </li>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:DishManagement">
        <li id="firstMenu3" class="active hidden">
            <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">菜品管理</span>
                <i class="fa fa-angle-right angle-right"></i></a>
            <ul>
                <li <c:if test="${MethodModule eq 'Admin:DishManagement:Unit:List'}">class="active" </c:if>>
                    <a class="J_menu" href="${website}admin/dish/unit">菜品单位管理</a>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:DishManagement:Tag:List'}">class="active" </c:if>>
                    <a class="J_menu" href="${website}admin/dish/tag">菜品分类管理</a>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:DishManagement:Taste:List'}">class="active" </c:if>>
                    <a class="J_menu" href="${website}admin/dish/taste">菜品口味管理</a>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:DishManagement:CostCard'}">class="active" </c:if>>
                    <a class="J_menu" href="${website}admin/dish/cost/card">菜品成本卡管理</a>
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
                <li <c:if test="${MethodModule eq 'Admin:DishManagement:Package'}">class="active"</c:if> >
                    <a class="J_menu" href="${website}admin/dish/package">套餐管理
                        <c:if test="${ExtModule eq 'Admin:DishManagement:Package:New'
                                or ExtModule eq 'Admin:DishManagement:Package:Update'}">
                            <i class="fa fa-angle-right angle-right"></i>
                        </c:if>
                    </a>
                    <c:if test="${ExtModule eq 'Admin:DishManagement:Package:New'
                                or ExtModule eq 'Admin:DishManagement:Package:Update'}">
                        <ul>
                            <li <c:if test="${ExtModule eq 'Admin:DishManagement:Package:New'}">class="active"</c:if> >
                                <a href="${website}admin/dish/package/new" >添加套餐</a>
                            </li>
                            <c:if test="${ExtModule eq 'Admin:DishManagement:Package:Update'}">
                                <li class="active"><a href="${website}admin/dish/package/update">编辑套餐</a></li>
                            </c:if>
                        </ul>
                    </c:if>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:DishManagement:Feature'}">class="active"</c:if> >
                    <a class="J_menu" href="${website}admin/dish/feature">本店特色</a>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:DishManagement:TodayCheap'}">class="active"</c:if> >
                    <a class="J_menu" href="${website}admin/dish/today/cheap">今日特价</a>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:DishManagement:SaleRanking'}">class="active"</c:if> >
                    <a class="J_menu" href="${website}admin/dish/sale/ranking">销量排行</a>
                </li>
            </ul>
        </li>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:BasicInfo">
        <li id="firstMenu4" class="active hidden">
            <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">原配料管理</span><i
                    class="fa fa-angle-right angle-right"></i></a>
            <ul>
                <li><a class="J_menu" href="javascript:;">原配料单位管理</a></li>
                <li><a class="J_menu" href="javascript:;">原配料管理</a></li>
            </ul>
        </li>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:User:Management">
        <li id="firstMenu5" class="active hidden">
            <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">用户信息管理</span><i
                    class="fa fa-angle-right angle-right"></i></a>
            <ul>
                <shiro:checkPermission name="Admin:User:Management:Employee">
                <li <c:if test="${MethodModule eq 'Admin:User:Management:Employee'}">class="active"</c:if>>
                    <c:if test="${MethodModule eq 'Admin:User:Management:Employee'}">
                        <a class="J_menu" href="${website}admin/party/group/employee/">员工管理</a>
                    </c:if>
                    <c:if test="${ExtModule eq 'Admin:User:Management:Employee:New'
                                            or ExtModule eq 'Admin:User:Management:Employee:Update'}">
                        <ul>
                            <li <c:if test="${ExtModule eq 'Admin:User:Management:Employee:New'}">class="active" </c:if>>
                                <a href="${website}admin/party/group/employee/add">添加员工</a>
                            </li>
                            <c:if test="${ExtModule eq 'Admin:User:Management:Employee:Update'}">
                                <li class="active">
                                    <a href="#">编辑员工</a>
                                </li>
                            </c:if>
                        </ul>
                    </c:if>
                </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:User:Management:Vip">
                <li <c:if test="${MethodModule eq 'Admin:User:Management:Vip:VipInfo'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/party/group/vip/">会员管理</a>
                    <c:if test="${ExtModule eq 'Admin:User:Management:Vip:VipInfo:New'
                                            or ExtModule eq 'Admin:User:Management:Vip:VipInfo:Update'
                                            or ExtModule eq 'Admin:User:Management:Vip:VipInfo:Detail'
                                            or ExtModule eq 'Admin:User:Management:Vip:VipInfo:Consumption'}">
                        <ul>
                            <li <c:if test="${ExtModule eq 'Admin:User:Management:Vip:VipInfo:New'}">class="active" </c:if>>
                                <a href="${website}admin/party/group/vip/new">添加会员</a>
                            </li>
                            <c:if test="${ExtModule eq 'Admin:User:Management:Vip:VipInfo:Update'}">
                                <li class="active">
                                    <a href="#">修改会员</a>
                                </li>
                            </c:if>
                            <c:if test="${ExtModule eq 'Admin:User:Management:Vip:VipInfo:Detail'}">
                                <li class="active">
                                    <a href="#">查看详情</a>
                                </li>
                            </c:if>
                            <c:if test="${ExtModule eq 'Admin:User:Management:Vip:VipInfo:Consumption'}">
                                <li class="active">
                                    <a href="#">消费详情列表</a>
                                </li>
                            </c:if>
                        </ul>
                    </c:if>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:Vip:VipDishPrice'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/vip/price/plan/list">会员价方案管理</a>
                    <c:if test="${ExtModule eq 'Admin:Vip:VipDishPrice:List'
                                            or ExtModule eq 'Admin:Vip:VipDishPrice:Plan:Update'}">
                        <ul>
                            <li class="active">
                                <a href="">会员价管理</a>
                            </li>
                        </ul>
                    </c:if>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:Vip:RechargePlan:List'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/vip/recharge/plan/list">会员充值方案管理</a>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:Vip:Grade'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/vip/grade">
                        会员等级管理
                        <c:if test="${ExtModule eq 'Admin:Vip:Grade:New'
                                    or ExtModule eq 'Admin:Vip:Grade:Update'}"><i class="fa fa-angle-right angle-right"></i></c:if>
                    </a>
                    <c:if test="${ExtModule eq 'Admin:Vip:Grade:New'
                                or ExtModule eq 'Admin:Vip:Grade:Update'}">
                        <ul>
                            <li <c:if test="${ExtModule eq 'Admin:Vip:Grade:New'}">class="active" </c:if>>
                                <a href="${website}admin/vip/grade/new">添加会员等级</a>
                            </li>
                            <c:if test="${ExtModule eq 'Admin:Vip:Grade:Update'}">
                                <li class="active">
                                    <a href="#">编辑会员等级</a>
                                </li>
                            </c:if>
                        </ul>
                    </c:if>
                    <c:if test="${ExtModule eq 'Admin:Vip:Grade:Integrate:List'}">
                        <ul>
                            <li <c:if test="${ExtModule eq 'Admin:Vip:Grade:Integrate:List'}">class="active"</c:if> >
                                <a href="#" >会员积分管理</a>
                            </li>
                        </ul>
                    </c:if>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:Vip:MultipleIntegralPlan:List'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/vip/multiple/integral/plan">多倍积分方案管理</a>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:Vip:Account:List'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/vip/account/list">会员账户信息管理</a>
                </li>
                <li <c:if test="${MethodModule eq 'Admin:Vip:Card:List'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/vip/card">会员卡管理</a>
                </li>
                </shiro:checkPermission>
            </ul>

        </li>
    </shiro:checkPermission>
    <shiro:checkPermission name="Admin:Storage">
        <li id="firstMenu6" class="active hidden">
            <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">库存管理</span><i
                    class="fa fa-angle-right angle-right"></i></a>
            <ul>
                <shiro:checkPermission name="Admin:Storage:Supplier:List">
                    <li <c:if test="${MethodModule eq 'Admin:Storage:Supplier:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/storage/supplier">供货商管理</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Storage:Tag:List">
                    <li <c:if test="${MethodModule eq 'Admin:Storage:Tag:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/storage/tag">库存分类管理</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Storage:Ingredient">
                    <li <c:if test="${MethodModule eq 'Admin:Storage:Ingredient'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/storage/ingredient">库存原配料管理</a>
                        <c:if test="${ExtModule eq 'Admin:Storage:Ingredient:New'
                                            or ExtModule eq 'Admin:Storage:Ingredient:Update'}">
                            <ul>
                                <li <c:if test="${ExtModule eq 'Admin:Storage:Ingredient:New'}">class="active" </c:if>>
                                    <a href="${website}admin/storage/ingredient/tonew">添加原配料</a>
                                </li>
                                <c:if test="${ExtModule eq 'Admin:Storage:Ingredient:Update'}">
                                    <li class="active">
                                        <a href="#">修改原配料</a>
                                    </li>
                                </c:if>
                            </ul>
                        </c:if>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Storage:Item">
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
                                <shiro:checkPermission name="Admin:Storage:Item:New">
                                    <li <c:if test="${ExtModule eq 'Admin:Storage:Item:New'}">class="active" </c:if>>
                                        <a href="${website}admin/storage/item/tonew">添加库存物品</a>
                                    </li>
                                </shiro:checkPermission>
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
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Storage:Report:List">
                    <li <c:if test="${MethodModule eq 'Admin:Storage:Report:List'}">class="active" </c:if>>
                        <a class="J_menu" href="${website}admin/storage/report">库存单据管理</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Storage:Settlement:Check:List">
                    <li <c:if test="${MethodModule eq 'Admin:Storage:Settlement:Check:List'}">class="active" </c:if>>
                        <a class="J_menu" href="${website}admin/storage/settlement/check">库存盘点</a>
                    </li>
                </shiro:checkPermission>
                <shiro:checkPermission name="Admin:Storage:Settlement:Supplier:List">
                    <li <c:if test="${MethodModule eq 'Admin:Storage:Settlement:Supplier:List'}">class="active" </c:if>>
                        <a class="J_menu" href="${website}admin/storage/settlement/supplier">结算中心</a>
                    </li>
                </shiro:checkPermission>
                <li><a class="J_menu" href="javascript:;">库存更新管理</a></li>
                <li><a class="J_menu" href="javascript:;">预警管理</a></li>
                <shiro:checkPermission name="Admin:Storage:Depot:List">
                    <li <c:if test="${MethodModule eq 'Admin:Storage:Depot:List'}">class="active" </c:if>>
                        <a class="J_menu" href="${website}admin/storage/depot">存放点管理</a>
                    </li>
                </shiro:checkPermission>
            </ul>
        </li>
    </shiro:checkPermission>

    <shiro:checkPermission name="Admin:Stock">
        <li id="firstMenu6.5" class="active hidden">
            <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">库存管理2.0</span><i
                    class="fa fa-angle-right angle-right"></i></a>
            <ul>
                <shiro:checkPermission name="Admin:Stock:Specifications:List">
                    <li <c:if test="${MethodModule eq 'Admin:Stock:item:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/stock/item">库存物品管理</a>
                    </li>
                </shiro:checkPermission>
            </ul>
            <ul>
                <shiro:checkPermission name="Admin:Stock:Specifications:List">
                    <li <c:if test="${MethodModule eq 'Admin:Stock:kitchen:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/stock/kitchen">厨房管理</a>
                    </li>
                </shiro:checkPermission>
            </ul>
            <ul>
                <shiro:checkPermission name="Admin:Stock:Specifications:List">
                    <li <c:if test="${MethodModule eq 'Admin:Stock:kitchenItem:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/stock/kitchenItem">厨房物品管理</a>
                    </li>
                </shiro:checkPermission>
            </ul>
            <ul>
                <shiro:checkPermission name="Admin:Stock:Specifications:List">
                    <li <c:if test="${MethodModule eq 'Admin:Stock:Specifications:List'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/stock/specifications">规格管理</a>
                    </li>
                </shiro:checkPermission>
            </ul>
        </li>
    </shiro:checkPermission>

    <shiro:checkPermission name = "Admin:Revenue:Count">
    <li id="firstMenu7" class="active hidden">
        <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">营收统计</span><i
                class="fa fa-angle-right angle-right"></i></a>
        <ul>
            <shiro:checkPermission name="Admin:Revenue:Count:Checkout">
                <li <c:if test="${MethodModule eq 'Admin:Revenue:Count:Checkout'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/revenue/checkout">账单统计</a>
                </li>
            </shiro:checkPermission>
                <li><a class="J_menu" href="javascript:;">会员充值统计</a></li>
            <shiro:checkPermission name="Admin:Revenue:Count:BackDish">
                <li <c:if test="${MethodModule eq 'Admin:Revenue:Count:BackDish'}">class="active"</c:if>>
                    <a class="J_menu" href="${website}admin/revenue/backdish">退项清单</a>
                </li>
            </shiro:checkPermission>
        </ul>
    </li>
    </shiro:checkPermission>

    <shiro:checkPermission name="Admin:Rank:Count">
        <li id="firstMenu8" class="active hidden">
            <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">营业分析</span><i
                    class="fa fa-angle-right angle-right"></i></a>
            <ul>
                <shiro:checkPermission name="Admin:Rank:Count:SaleRanking">
                    <li <c:if test="${MethodModule eq 'Admin:Rank:Count:SaleRanking'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/rank/sale">菜品销售排行</a>
                    </li>
                </shiro:checkPermission>
                    <li><a class="J_menu" href="javascript:;">菜品销售趋势排行</a></li>
                <shiro:checkPermission name="Admin:Rank:Count:TagRanking">
                    <li <c:if test="${MethodModule eq 'Admin:Rank:Count:TagRanking'}">class="active"</c:if>>
                        <a class="J_menu" href="${website}admin/rank/bigtag">菜品大类销售排行</a>
                    </li>
                </shiro:checkPermission>
                    <li><a class="J_menu" href="javascript:;">餐台使用排行</a></li>
                    <li><a class="J_menu" href="javascript:;">餐台区域使用排行</a></li>
            </ul>
        </li>
    </shiro:checkPermission>

    <shiro:checkPermission name="Admin:SAdmin:Party">
        <li id="firstMenu9" class="active hidden">
            <a href="javascript:;"><i class="fa fa-list"></i>&nbsp;<span class="J_firstMenu">超级管理</span><i
                    class="fa fa-angle-right angle-right"></i></a>
            <ul>
                <shiro:checkPermission name="Admin:SAdmin:Party:Security">
                    <li <c:if test="${MethodModule eq 'Admin:SAdmin:Party:Security'}">class="active"</c:if> >
                        <a class="J_menu" href="javascript:;">权限管理<i class="fa fa-angle-right angle-right"></i></a>
                        <ul <c:if test="${MethodModule != 'Admin:SAdmin:Party:Security'}">class="hidden"</c:if>>

                            <shiro:checkPermission name="Admin:SAdmin:Party:Security:Permission:List">
                                <li <c:if test="${ExtModule eq 'Admin:SAdmin:Party:Security:Permission:List'}">class="active"</c:if>>
                                    <a href="${website}admin/party/security/permission">基本权限管理</a>
                                </li>
                           </shiro:checkPermission>

                            <shiro:checkPermission name="Admin:SAdmin:Party:Security:Group:List">
                                <li <c:if test="${ExtModule eq 'Admin:SAdmin:Party:Security:Group:List'}">class="active"</c:if>>
                                    <a href="${website}admin/party/security/group">安全组管理</a>
                                </li>
                            </shiro:checkPermission>
                            <c:if test="${ExtModule eq 'Admin:SAdmin:Party:Security:Group:Permission:List'}">
                                <li class="active">
                                    <a href="#">安全组权限管理</a>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </shiro:checkPermission>
                <li><a class="J_menu" href="javascript:;">菜品销售趋势排行</a></li>
                <li><a class="J_menu" href="javascript:;">菜品大类销售排行</a></li>
                <li><a class="J_menu" href="javascript:;">餐台使用排行</a></li>
                <li><a class="J_menu" href="javascript:;">餐台区域使用排行</a></li>
            </ul>
        </li>
    </shiro:checkPermission>

</ul>


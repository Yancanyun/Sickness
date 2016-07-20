package com.emenu.common.enums.other;

/**
 * ModuleEnums
 *
 * @author: zhangteng
 * @time: 15/10/16 上午10:42
 */
public enum ModuleEnums {

    Null(""),

    // 后台
    Admin("Admin"),

    // 后台首页
    AdminIndex("Admin:Index"),

    // 超级管理
    AdminSAdmin("Admin:SAdmin"),

    // 权限
    AdminParty("Admin:SAdmin:Party"),
    AdminPartySecurity("Admin:SAdmin:Party:Security"),
    // 基础权限管理
    AdminPartySecurityPermission("Admin:SAdmin:Party:Security:Permission"),
    AdminPartySecurityPermissionList("Admin:SAdmin:Party:Security:Permission:List"),
    AdminPartySecurityPermissionNew("Admin:SAdmin:Party:Security:Permission:New"),
    AdminPartySecurityPermissionUpdate("Admin:SAdmin:Party:Security:Permission:Update"),
    AdminPartySecurityPermissionDelete("Admin:SAdmin:Party:Security:Permission:Delete"),
    // 安全组管理
    AdminPartySecurityGroup("Admin:SAdmin:Party:Security:Group"),
    AdminPartySecurityGroupList("Admin:SAdmin:Party:Security:Group:List"),
    AdminPartySecurityGroupNew("Admin:SAdmin:Party:Security:Group:New"),
    AdminPartySecurityGroupUpdate("Admin:SAdmin:Party:Security:Group:Update"),
    AdminPartySecurityGroupDel("Admin:SAdmin:Party:Security:Group:Del"),
    AdminPartySecurityGroupPermission("Admin:SAdmin:Party:Security:Group:Permission"),
    AdminPartySecurityGroupPermissionList("Admin:SAdmin:Party:Security:Group:Permission:List"),
    AdminPartySecurityGroupPermissionNew("Admin:SAdmin:Party:Security:Group:Permission:New"),
    AdminPartySecurityGroupPermissionDelete("Admin:SAdmin:Party:Security:Group:Permission:Delete"),

    // 基本信息管理
    AdminBasicInfo("Admin:BasicInfo"),
    // 搜索风向标
    AdminBasicInfoKeywords("Admin:BasicInfo:Keywords"),
    AdminBasicInfoKeywordsList("Admin:BasicInfo:Keywords:List"),
    AdminBasicInfoKeywordsNew("Admin:BasicInfo:Keywords:New"),
    AdminBasicInfoKeywordsDel("Admin:BasicInfo:Keywords:Del"),
    // 点餐平台首页
    AdminBasicInfoIndexImg("Admin:BasicInfo:IndexImg"),
    AdminBasicInfoIndexImgList("Admin:BasicInfo:IndexImg:List"),
    AdminBasicInfoIndexImgNew("Admin:BasicInfo:IndexImg:New"),
    AdminBasicInfoIndexImgDel("Admin:BasicInfo:IndexImg:Del"),
    AdminBasicInfoIndexImgUpdate("Admin:BasicInfo:IndexImg:Update"),
    //打印机管理
    AdminBasicInfoPrinter("Admin:BasicInfo:Printer"),
    AdminBasicInfoPrinterList("Admin:BasicInfo:Printer:List"),
    AdminBasicInfoPrinterNew("Admin:BasicInfo:Printer:New"),
    AdminBasicInfoPrinterDel("Admin:BasicInfo:Printer:Del"),
    AdminBasicInfoPrinterUpdate("Admin:BasicInfo:Printer:Update"),


    //饭店管理
    AdminRestaurant("Admin:Restaurant"),
    //区域管理
    AdminRestaurantArea("Admin:Restaurant:Area"),
    AdminRestaurantAreaList("Admin:Restaurant:Area:List"),
    AdminRestaurantAreaNew("Admin:Restaurant:Area:New"),
    AdminRestaurantAreaUpdate("Admin:Restaurant:Area:Update"),
    AdminRestaurantAreaDel("Admin:Restaurant:Area:Del"),
    //餐台管理
    AdminRestaurantTable("Admin:Restaurant:Table"),
    AdminRestaurantTableList("Admin:Restaurant:Table:List"),
    AdminRestaurantTableNew("Admin:Restaurant:Table:New"),
    AdminRestaurantTableUpdate("Admin:Restaurant:Table:Update"),
    AdminRestaurantTableDel("Admin:Restaurant:Table:Del"),
    //二维码管理
    AdminRestaurantQrCode("Admin:Restaurant:QrCode"),
    AdminRestaurantQrCodeList("Admin:Restaurant:QrCode:List"),
    AdminRestaurantQrCodeNew("Admin:Restaurant:QrCode:New"),
    AdminRestaurantQrCodeDownload("Admin:Restaurant:QrCode:Download"),
    //餐段管理
    AdminRestaurantMealPeriod("Admin:Restaurant:MealPeriod"),
    AdminRestaurantMealPeriodList("Admin:Restaurant:MealPeriod:List"),
    AdminRestaurantMealPeriodNew("Admin:Restaurant:MealPeriod:New"),
    AdminRestaurantMealPeriodUpdate("Admin:Restaurant:MealPeriod:Update"),
    AdminRestaurantMealPeriodDel("Admin:Restaurant:MealPeriod:Del"),
    //备注管理
    AdminRestaurantRemark("Admin:Restaurant:Remark"),
    AdminRestaurantRemarkList("Admin:Restaurant:Remark:List"),
    AdminRestaurantRemarkNew("Admin:Restaurant:Remark:New"),
    AdminRestaurantRemarkUpdate("Admin:Restaurant:Remark:Update"),
    AdminRestaurantRemarkDel("Admin:Restaurant:Remark:Del"),
    //服务类型管理
    AdminRestaurantCallWaiter("Admin:Restaurant:CallWaiter"),
    AdminRestaurantCallWaiterList("Admin:Restaurant:CallWaiter:List"),
    AdminRestaurantCallWaiterNew("Admin:Restaurant:CallWaiter:New"),
    AdminRestaurantCallWaiterDel("Admin:Restaurant:CallWaiter:Del"),
    AdminRestaurantCallWaiterUpdate("Admin:Restaurant:CallWaiter:Update"),

    // 菜品管理
    AdminDishManagement("Admin:DishManagementAdmin:DishManagement"),
    // 菜品
    AdminDish("Admin:DishManagement:Dish"),
    AdminDishList("Admin:DishManagement:Dish:List"),
    AdminDishNew("Admin:DishManagement:Dish:New"),
    AdminDishUpdate("Admin:DishManagement:Dish:Update"),
    AdminDishDelete("Admin:DishManagement:Dish:Delete"),
    // 菜品单位管理
    AdminDishUnit("Admin:DishManagement:Unit"),
    AdminDishUnitList("Admin:DishManagement:Unit:List"),
    AdminDishUnitNew("Admin:DishManagement:Unit:New"),
    AdminDishUnitUpdate("Admin:DishManagement:Unit:Update"),
    AdminDishUnitDel("Admin:DishManagement:Unit:Del"),
    // 菜品口味管理
    AdminDishTaste("Admin:DishManagement:Taste"),
    AdminDishTasteList("Admin:DishManagement:Taste:List"),
    AdminDishTasteNew("Admin:DishManagement:Taste:New"),
    AdminDishTasteUpdate("Admin:DishManagement:Taste:Update"),
    AdminDishTasteDel("Admin:DishManagement:Taste:Del"),
    // 菜品分类管理
    AdminDishTag("Admin:DishManagement:Tag"),
    AdminDishTagList("Admin:DishManagement:Tag:List"),
    AdminDishTagNew("Admin:DishManagement:Tag:New"),
    AdminDishTagUpdate("Admin:DishManagement:Tag:Update"),
    AdminDishTagDel("Admin:DishManagement:Tag:Del"),
    //菜品成本卡管理
    AdminDishCostCard("Admin:DishManagement:CostCard"),
    AdminDishCostCardList("Admin:DishManagement:CostCard:List"),
    AdminDishCostCardNew("Admin:DishManagement:CostCard:New"),
    AdminDishCostCardDel("Admin:DishManagement:CostCard:Del"),
    // 本店特色
    AdminDishFeature("Admin:DishManagement:Feature"),
    AdminDishFeatureList("Admin:DishManagement:Feature:List"),
    AdminDishFeatureNew("Admin:DishManagement:Feature:New"),
    AdminDishFeatureDel("Admin:DishManagement:Feature:Del"),
    // 今日特价
    AdminDishTodayCheap("Admin:DishManagement:TodayCheap"),
    AdminDishTodayCheapList("Admin:DishManagement:TodayCheap:List"),
    AdminDishTodayCheapNew("Admin:DishManagement:TodayCheap:New"),
    AdminDishTodayCheapDel("Admin:DishManagement:TodayCheap:Del"),
    // 销量排行
    AdminDishSaleRanking("Admin:DishManagement:SaleRanking"),
    AdminDishSaleRankingList("Admin:DishManagement:SaleRanking:List"),
    AdminDishSaleRankingNew("Admin:DishManagement:SaleRanking:New"),
    AdminDishSaleRankingDel("Admin:DishManagement:SaleRanking:Del"),
    // 套餐管理
    AdminDishPackage("Admin:DishManagement:Package"),
    AdminDishPackageList("Admin:DishManagement:Package:List"),
    AdminDishPackageNew("Admin:DishManagement:Package:New"),
    AdminDishPackageDel("Admin:DishManagement:Package:Del"),
    AdminDishPackageUpdate("Admin:DishManagement:Package:Update"),


    //用户管理
    AdminUserManagement("Admin:User:Management"),

    //会员基本信息管理
    AdminVipInfo("Admin:User:Management:Vip:VipInfo"),
    AdminVipInfoList("Admin:User:Management:Vip:VipInfo:List"),
    AdminVipInfoNew("Admin:User:Management:Vip:VipInfo:New"),
    AdminVipInfoUpdate("Admin:User:Management:Vip:VipInfo:Update"),
    AdminVipInfoDetail("Admin:User:Management:Vip:VipInfo:Detail"),
    AdminVipInfoDel("Admin:User:Management:Vip:VipInfo:Del"),
    AdminVipInfoSearch("Admin:User:Management:Vip:VipInfo:Search"),

    //员工管理

    AdminUserManagementEmployee("Admin:User:Management:Employee"),
    AdminUserManagementEmployeeList("Admin:User:Management:Employee:List"),
    AdminUserManagementEmployeeUpdate("Admin:User:Management:Employee:Update"),
    AdminUserManagementEmployeeDelete("Admin:User:Management:Employee:Delete"),
    AdminUserManagementEmployeeNew("Admin:User:Management:Employee:New"),

    // 库存管理
    AdminStorage("Admin:Storage"),

    //库存单据
    AdminStorageReport("Admin:Storage:Report"),
    AdminStorageReportList("Admin:Storage:Report:List"),
    AdminStorageReportNew("Admin:Storage:Report:New"),
    AdminStorageReportUpdate("Admin:Storage:Report:Update"),
    AdminStorageReportDelete("Admin:Storage:Report:Delete"),
    AdminStorageReportSearch("Admin:Storage:Report:Search"),
    AdminStorageReportExport("Admin:Storage:Report:Export"),
    //库存存放点管理
    AdminStorageDepot("Admin:Storage:Depot"),
    AdminStorageDepotList("Admin:Storage:Depot:List"),
    AdminStorageDepotNew("Admin:Storage:Depot:New"),
    AdminStorageDepotDelete("Admin:Storage:Depot:Delete"),
    AdminStorageDepotUpdate("Admin:Storage:Depot:Update"),
    AdminStorageDepotSearch("Admin:Storage:Depot:Search"),
    //库存结算中心
    AdminStorageSettlementSupplier("Admin:Storage:Settlement:Supplier"),
    AdminStorageSettlementSupplierList("Admin:Storage:Settlement:Supplier:List"),
    AdminStorageSettlementSupplierExport("Admin:Storage:Settlement:Supplier:Export"),
    //库存盘点
    AdminStorageSettlementCheck("Admin:Storage:Settlement:Check"),
    AdminStorageSettlementCheckList("Admin:Storage:Settlement:Check:List"),
    AdminStorageSettlementCheckExport("Admin:Storage:Settlement:Check:Export"),

    // 供货商管理
    AdminStorageSupplier("Admin:Storage:Supplier"),
    AdminStorageSupplierList("Admin:Storage:Supplier:List"),
    AdminStorageSupplierNew("Admin:Storage:Supplier:New"),
    AdminStorageSupplierUpdate("Admin:Storage:Supplier:Update"),
    AdminStorageSupplierDelete("Admin:Storage:Supplier:Delete"),
    // 库存分类管理
    AdminStorageTag("Admin:Storage:Tag"),
    AdminStorageTagList("Admin:Storage:Tag:List"),
    AdminStorageTagNew("Admin:Storage:Tag:New"),
    AdminStorageTagUpdate("Admin:Storage:Tag:Update"),
    AdminStorageTagDelete("Admin:Storage:Tag:Delete"),

    // 库存原配料
    AdminStorageIngredient("Admin:Storage:Ingredient"),
    AdminStorageIngredientList("Admin:Storage:Ingredient:List"),
    AdminStorageIngredientNew("Admin:Storage:Ingredient:New"),
    AdminStorageIngredientUpdate("Admin:Storage:Ingredient:Update"),
    AdminStorageIngredientExport("Admin:Storage:Ingredient:Export"),
    AdminStorageIngredientDelete("Admin:Storage:Ingredient:delete"),

    // 库存物品管理
    AdminStorageItem("Admin:Storage:Item"),
    AdminStorageItemList("Admin:Storage:Item:List"),
    AdminStorageItemNew("Admin:Storage:Item:New"),
    AdminStorageItemUpdate("Admin:Storage:Item:Update"),
    AdminStorageItemDelete("Admin:Storage:Item:Delete"),
    AdminStorageItemUnitConversion("Admin:Storage:Item:UnitConversion"),
    AdminStorageItemUnitConversionList("Admin:Storage:Item:UnitConversion:List"),
    AdminStorageItemUnitConversionUpdate("Admin:Storage:Item:UnitConversion:Update"),


    //会员管理
    AdminVip("Admin:Vip"),

    //会员价方案管理
    AdminVipVipDishPrice("Admin:Vip:VipDishPrice"),
    AdminVipVipDishPricePlan("Admin:Vip:VipDishPrice:Plan"),
    AdminVipVipDishPricePlanList("Admin:Vip:VipDishPrice:Plan:List"),
    AdminVipVipDishPricePlanNew("Admin:Vip:VipDishPrice:Plan:New"),
    AdminVipVipDishPricePlanUpdate("Admin:Vip:VipDishPrice:Plan:Update"),
    AdminVipVipDishPricePlanDelete("Admin:Vip:VipDishPrice:Plan:Delete"),

    //会员价管理
    AdminVipVipDishPriceList("Admin:Vip:VipDishPrice:List"),
    AdminVipVipDishPriceUpdate("Admin:Vip:VipDishPrice:Update"),

    //多倍积分方案管理
    AdminVipMultipleIntegralPlan("Admin:Vip:MultipleIntegralPlan"),
    AdminVipMultipleIntegralPlanList("Admin:Vip:MultipleIntegralPlan:List"),
    AdminVipMultipleIntegralPlanNew("Admin:Vip:MultipleIntegralPlan:New"),
    AdminVipMultipleIntegralPlanUpdate("Admin:Vip:MultipleIntegralPlan:Update"),
    AdminVipMultipleIntegralPlanDelete("Admin:Vip:MultipleIntegralPlan:Delete"),

    //会员充值方案管理
    AdminVipRechargePlan("Admin:Vip:RechargePlan"),
    AdminVipRechargePlanList("Admin:Vip:RechargePlan:List"),
    AdminVipRechargePlanNew("Admin:Vip:RechargePlan:New"),
    AdminVipRechargePlanUpdate("Admin:Vip:RechargePlan:Update"),
    AdminVipRechargePlanDel("Admin:Vip:RechargePlan:Del"),
    //会员等级方案管理
    AdminVipGrade("Admin:Vip:Grade"),
    AdminVipGradeList("Admin:Vip:Grade:List"),
    AdminVipGradeNew("Admin:Vip:Grade:New"),
    AdminVipGradeUpdate("Admin:Vip:Grade:Update"),
    AdminVipGradeDelete("Admin:Vip:Grade:Delete"),
    //会员积分方案管理
    AdminVipIntegratePlan("Admin:Vip:Grade:Integrate"),
    AdminVipIntegratePlanList("Admin:Vip:Grade:Integrate:List"),
    AdminVipIntegratePlanNew("Admin:Vip:Grade:Integrate:New"),
    AdminVipIntegratePlanUpdate("Admin:Vip:Grade:Integrate:Update"),
    AdminVipIntegratePlanDel("Admin:Vip:Grade:Integrate:Del"),
    //会员卡管理
    AdminVipCard("Admin:Vip:Card"),
    AdminVipCardList("Admin:Vip:Card:List"),
    AdminVipCardNew("Admin:Vip:Card:New"),
    AdminVipCardUpdate("Admin:Vip:Card:Update"),
    AdminVipCardDel("Admin:Vip:Card:Del"),
    //会员账号管理
    AdminVipAccount("Admin:Vip:Account"),
    AdminVipAccountList("Admin:Vip:Account:List"),
    AdminVipAccountUpdateStatus("Admin:Vip:Account:Update:Status"),




    // 顾客点餐平台
    Mobile("Mobile"),
    // 首页
    MobileIndex("Mobile:Index"),
    // 菜品
    MobileDish("Mobile:Dish"),
    // 点菜
    MobileDishNew("Mobile:Dish:New"),
    // 点赞
    MobileDishLike("Mobile:Dish:Like"),
    // 菜品选择图片版
    MobileDishImage("Mobile:Dish:Image"),
    MobileDishImageList("Mobile:Dish:Image:List"),
    // 菜品选择文字版
    MobileDishText("Mobile:Dish:Text"),
    MobileDishTextList("Mobile:Dish:Text:List"),
    // 菜品详情
    MobileDishDetail("Mobile:Dish:Detail"),
    MobileDishDetailList("Mobile:Dish:Detail:List"),
    //猜你喜欢
    MobileDishPrefer("Mobile:Dish:Prefer"),
    MobileDishPreferCheapList("Mobile:Dish:Prefer:Cheap:List"),
    MobileDishPreferFeatureList("Mobile:Dish:Prefer:Feature:List"),
    MobileDishPreferRankList("Mobile:Dish:Prefer:Rank:List"),
    //呼叫服务
    MobileCall("Mobile:Call"),
    //我的订单
    MobileMyOrder("Mobile:Order"),
    MobileMyOrderList("Mobile:Order:List"),
    MobileMyOrderDel("Mobile:Order:Del"),
    MobileMyOrderQuantityChange("Mobile:Order:Quantity:Change"),
    MobileMyOrderTotalMoney("Mobile:Order:Money"),


    //服务员模块
    Waiter("Waiter"),
    //服务员餐台模块
    WaiterTable("Waiter:Table"),
    //服务员查看餐台区域列表
    WaiterTableAreaList("Waiter:Table:Area:List"),
    //服务员查看餐台列表
    WaiterTableList("Waiter:Table:List"),
    //服务员开台
    WaiterTableOpen("Waiter:Table:Open"),
    //服务员换台
    WaiterTableChange("Waiter:Table:Change"),
    //服务员清台
    WaiterTableClean("Waiter:Table:Clean"),
    //服务员点菜
    WaiterOrderDish("Waiter:Table:Order"),
    //服务员并台
    WaiterTableMerge("Waiter:Table:Merge"),
    //服务员查看所有呼叫服务
    WaiterCallList("Waiter:Call:List"),
    // 服务员登录
    WaiterLogin("Waiter:Login"),
    //服务员退菜
    WaiterBackDish("Waiter:Back:Dish"),
    //服务员端显示点了的缓存中的菜品
    WaiterOrderList("Waiter:Order:List"),
    //服务员端修改菜品数量
    WaiterOrderDishChangeQuantity("Waiter:Order:Dish:Quantity:Change"),
    //服务员端删除菜品
    WaiterOrderDishDelete("Waiter:Order:Dish:Delete"),
    //服务员端删除菜品
    WaiterOrderConfirm("Waiter:Order:Confirm"),



    //吧台客户端模块
    //吧台
    Bar("Bar"),
    //吧台客户端餐台
    BarTable("Bar:Table"),
    //吧台客户端餐台区域列表
    BarTableAreaList("Bar:Table:Area:List"),
    //吧台客户端餐台列表
    BarTableList("Bar:Table:List"),
    //吧台客户端餐台详细信息
    BarTableDetail("Bar:Table:Detail"),
    //吧台客户端餐台搜索
    BarTableSearch("Bar:Table:Search"),
    //吧台客户端开台
    BarTableOpen("Bar:Table:Open"),
    //吧台客户端换台
    BarTableChange("Bar:Table:Change"),
    //吧台客户端清台
    BarTableClean("Bar:Table:Clean"),
    //吧台客户端并台
    BarTableMerge("Bar:Table:Merge"),
    //吧台客户端餐台中的订单菜品
    BarTableOrderDish("Bar:Table:Order:Dish"),
    //吧台客户端登录
    BarLogin("Bar:Login"),
    //吧台餐段
    BarMealPeriod("Bar:Meal:Period"),

    // 吧台催菜
    BarCallDish("Bar:Call:Dish"),
    // 吧台退菜
    BarBackDish("Bar:Back:Dish"),
    // 吧台退菜列表
    BarBackDishList("Bar:Back:Dish:List"),
    // 吧台退菜确认
    BarBackDishConfirm("Bar:Back:Dish:Confirm"),
    // 吧台结账单
    BarCheckout("Bar:Checkout"),
    // 吧台打印消费清单
    BarCheckoutPrint("Bar:Checkout:Print"),



    //后厨管理模块
    Cook("Cook"),
    CookOrderList("Cook:Order:List"),
    CookOrderTableList("Cook:Order:Table:List"),
    CookPrintOrderDish("Cook:Order:Print"),
    CookOrderDishWipe("Cook:Order:Wipe"),
    ;

    private String name;

    ModuleEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

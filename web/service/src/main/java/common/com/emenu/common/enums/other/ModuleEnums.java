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

    // 菜品单位管理
    AdminDishUnit("Admin:Dish:Unit"),
    AdminDishUnitList("Admin:Dish:Unit:List"),
    AdminDishUnitNew("Admin:Dish:Unit:New"),
    AdminDishUnitUpdate("Admin:Dish:Unit:Update"),
    AdminDishUnitDel("Admin:Dish:Unit:Del"),

    // 菜品分类管理
    AdminDishTag("Admin:Dish:Tag"),
    AdminDishTagList("Admin:Dish:Tag:List"),
    AdminDishTagNew("Admin:Dish:Tag:New"),
    AdminDishTagUpdate("Admin:Dish:Tag:Update"),
    AdminDishTagDel("Admin:Dish:Tag:Del"),


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

    //库存存放点管理
    AdminStorageDepot("Admin:Storage:Depot"),
    AdminStorageDepotList("Admin:Storage:Depot"),
    AdminStorageDepotNew("Admin:Storage:Depot:New"),
    AdminStorageDepotDelete("Admin:Storage:Depot:Delete"),
    AdminStorageDepotUpdate("Admin:Storage:Depot:Update"),
    AdminStorageDepotSearch("Admin:Storage:Depot:Search"),


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
    AdminStorageTagDelete("Admin:Storage:Tag:Delete")
    ;

    private String name;

    ModuleEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

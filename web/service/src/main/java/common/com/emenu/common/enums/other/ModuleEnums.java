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


    // 饭店管理
    AdminRestaurant("Admin:Restaurant"),

    AdminRestaurantArea("Admin:Restaurant:Area"),
    AdminRestaurantAreaList("Admin:Restaurant:Area:List"),
    AdminRestaurantAreaNew("Admin:Restaurant:Area:New"),
    AdminRestaurantAreaUpdate("Admin:Restaurant:Area:Update"),
    AdminRestaurantAreaDel("Admin:Restaurant:Area:Del"),

    AdminRestaurantTable("Admin:Restaurant:Table"),
    AdminRestaurantTableList("Admin:Restaurant:Table:List"),
    AdminRestaurantTableNew("Admin:Restaurant:Table:New"),
    AdminRestaurantTableUpdate("Admin:Restaurant:Table:Update"),
    AdminRestaurantTableDel("Admin:Restaurant:Table:Del"),

    AdminRestaurantQrCode("Admin:Restaurant:QrCode"),
    AdminRestaurantQrCodeList("Admin:Restaurant:QrCode:List"),
    AdminRestaurantQrCodeNew("Admin:Restaurant:QrCode:New"),
    AdminRestaurantQrCodeDownload("Admin:Restaurant:QrCode:Download"),

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
    AdminDishTagDel("Admin:Dish:Tag:Del")
    ;
    private String name;

    ModuleEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

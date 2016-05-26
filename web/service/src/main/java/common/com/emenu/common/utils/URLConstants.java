package com.emenu.common.utils;

/**
 * 全站Url地址常量
 * 命名规范：大模块_小模块_小模块_..._URL
 * 必须全部是大写字母
 * 两个小模块之间的地址不空行
 * 两个大模块之间的地址用两个空行隔开
 *
 * @author: zhangteng
 * @time: 2014/9/25 20:06
 */
public final class URLConstants {

    //首页
    public static final String INDEX_URL = "";

    //home页
    public static final String HOME_URL = "home";

    /**********************后台*************************/
    public static final String ADMIN_URL = "admin";

    public static final String ADMIN_COMMON_URL = "admin/common";

    // 后台登录url
    public static final String ADMIN_LOGIN_URL = "admin/login";

    // 后台权限管理
    public static final String ADMIN_PARTY_SECURITY_PERMISSION_URL = "admin/party/security/permission";

    // 后台安全组管理
    public static final String ADMIN_PARTY_SECURITY_GROUP_URL = "admin/party/security/group";

    //搜索风向标url
    public static final String ADMIN_KEYWORDS_URL = "admin/keywords";

    //餐台区域管理页面
    public static final String ADMIN_AREA_URL = "admin/restaurant/area";

    //餐台管理页面
    public static final String ADMIN_TABLE_URL = "admin/restaurant/table";

    //餐台二维码管理页面
    public static final String ADMIN_QRCODE_URL = "admin/restaurant/qrcode";

    //点餐平台首页图片管理url
    public static final String ADMIN_INDEX_IMG_URL = "admin/index/img";

    //餐段管理
    public static final String ADMIN_MEAL_PERIOD_URL = "admin/restaurant/meal/period";

    //打印机管理
    public static final String ADMIN_PRINTER_URL = "admin/printer";

    //会员基本信息管理
    public static final String ADMIN_PARTY_VIP_VIPINFO_URL = "admin/party/group/vip";

    //员工管理
    public static final String EMPLOYEE_MANAGEMENT = "admin/party/group/employee";

    public static final String ADMIN_DISH_URL = "admin/dish";

    //成本卡管理
    public static final String ADMIN_COST_CARD_URL = "admin/dish/cost/card";


    // 菜品单位管理
    public static final String ADMIN_DISH_UNIT = "admin/dish/unit";

    // 菜品口味管理
    public static final String ADMIN_DISH_TASTE = "admin/dish/taste";

    // 菜品分类管理
    public static final String ADMIN_DISH_TAG = "admin/dish/tag";

    // 本店特色
    public static final String ADMIN_DISH_FEATURE_URL = "admin/dish/feature";

    // 今日特价
    public static final String ADMIN_DISH_TODAY_CHEAP_URL = "admin/dish/today/cheap";

    // 销量排行
    public static final String ADMIN_DISH_SALE_RANKING_URL = "admin/dish/sale/ranking";

    // 套餐管理
    public static final String ADMIN_DISH_PACKAGE_URL = "admin/dish/package";

    // 供应商管理
    public static final String ADMIN_STORAGE_SUPPLIER_URL = "admin/storage/supplier";

    // 库存结算中心
    public static final String ADMIN_STORAGE_SETTLEMENT_SUPPLIER_URL = "admin/storage/settlement/supplier";

    // 库存盘点
    public static final String ADMIN_STORAGE_SETTLEMENT_CHECK_URL = "admin/storage/settlement/check";

    // 库存分类管理
    public static final String ADMIN_STORAGE_TAG_URL = "admin/storage/tag";

    public static final String ADMIN_STORAGE_REPORT_URL = "admin/storage/report";

    // 存放点管理
    public static final String ADMIN_STORAGE_DEPOT_URL = "admin/storage/depot";

    public static final String ADMIN_STORAGE_ITEM_URL = "admin/storage/item";

    // 库存原配料管理
    public static final String ADMIN_STORAGE_INGREDIENT_URL = "admin/storage/ingredient";

    //备注管理
    public static final String ADMIN_REMARK_URL = "admin/restaurant/remark";

    //会员价方案管理
    public static final String ADMIN_VIP_VIP_DISH_PRICE_PLAN_URL = "admin/vip/price/plan";

    //会员价管理
    public static final String ADMIN_VIP_VIP_DISH_PRICE_URL = "admin/vip/price";

    //多倍积分方案
    public static final String ADMIN_VIP_MULTIPLE_INTEGRAL_PLAN_URL = "admin/vip/multiple/integral/plan";

    //会员充值方案管理
    public static final String ADMIN_VIP_RECHARGE_PLAN_URL = "admin/vip/recharge/plan";

    //会员等级方案管理
    public static final String ADMIN_VIP_GRADE_URL = "admin/vip/grade";

    //会员卡管理
    public static final String ADMIN_VIP_CARD_URL = "admin/vip/card";

    //会员账户管理
    public static final String ADMIN_VIP_ACCOUNT_URL = "admin/vip/account";

    //会员积分方案管理
    public static final String ADMIN_VIP_INTEGRAL_URL = "admin/vip/integral/plan";

    /**********************服务员APP*************************/
    //服务员查看餐台列表
    public static final String WAITER_TABLE_LIST_URL = "waiter/table/list";

    //服务员开台
    public static final String WAITER_TABLE_OPEN_URL = "waiter/table/open";

    //服务员换台
    public static final String WAITER_TABLE_CHANGE_URL = "waiter/table/change";

    //服务员清台
    public static final String WAITER_TABLE_CLEAN_URL = "waiter/table/clean";


    /**********************吧台客户端*************************/
    //吧台客户端餐台列表
    public static final String BAR_TABLE_URL = "bar/table";

    //吧台客户端开台
    public static final String BAR_TABLE_OPEN_URL = "bar/table/open";

    //吧台客户端换台
    public static final String BAR_TABLE_CHANGE_URL = "bar/table/change";

    //吧台客户端清台
    public static final String BAR_TABLE_CLEAN_URL = "bar/table/clean";
}

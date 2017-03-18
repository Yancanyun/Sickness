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

    //呼叫服务管理
    public static final String ADMIN_CALL_WAITER_URL = "admin/restaurant/call/waiter";

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

    // 统计菜品销售的排行
    public static final String ADMIN_COUNT_DISH_SALE_RANKING_URL = "admin/rank/sale";

    //统计菜品大类销售的排行
    public static final String ADMIN_COUNT_DISH_TAG_RANKING_URL = "admin/rank/bigtag";

    //账单稽查
    public static final String ADMIN_REVENUE_CHECKOUT_URL = "admin/revenue/checkout";

    // 退菜清单
    public static final String ADMIN_REVENUE_BACKDISH_URL = "admin/revenue/backdish";
    // 全局设置
    public static final String ADMIN_CONSTANT_URL = "admin/constant";

    // 新版库存-物品管理
    public static final String ADMIN_STOCK_ITEM_URL = "admin/stock/item";

    //新版库存-厨房管理
    public static final String ADMIN_STOCK_KITCHEN_URL = "admin/stock/kitchen";

    //新版库存-厨房物品管理
    public static final String ADMIN_STOCK_KITCHEN_ITEM_URL = "admin/stock/kitchen/item";

    //新版库存-规格管理
    public static final String ADMIN_STOCK_SPECIFICATIONS_URL="admin/stock/specifications";

    //新版库存-单据管理
    public static final String ADMIN_STOCK_DOCUMENTS_URL="admin/stock/documents";



    /**********************顾客点菜平台*************************/
    public static final String MOBILE_URL = "mobile";

    // 顾客点菜平台首页
    public static final String MOBILE_INDEX_URL = "mobile/index";

    // 顾客点菜
    public static final String MOBILE_DISH_URL = "mobile/dish";

    // 顾客点餐菜品文字列表
    public static final String MOBILE_DISH_TEXT_URL = "mobile/dish/text";

    // 菜品分类图片版
    public static final String MOBILE_DISH_IMAGE_URL = "mobile/dish/image";

    // 菜品详情
    public static final String MOBILE_DISH_DETAIL_URL = "mobile/dish/detail";

    //猜你喜欢
    public static final String MOBILE_DISH_PREFER_URL = "mobile/dish/prefer";

    //我的订单
    public static final String MOBILE_MY_ORDER_URL = "mobile/order";

    /**********************服务员APP*************************/
    // 服务员登录
    public static final String WAITER_LOGIN_URL = "waiter/login";

    // 餐台
    public static final String WAITER_TABLE_URL = "waiter/table";

    // 餐台区域
    public static final String WAITER_TABLE_AREA_URL = "waiter/table/area";

    //服务员开台
    public static final String WAITER_TABLE_OPEN_URL = "waiter/table/open";

    //服务员换台
    public static final String WAITER_TABLE_CHANGE_URL = "waiter/table/change";

    //服务员清台
    public static final String WAITER_TABLE_CLEAN_URL = "waiter/table/clean";

    // 服务员点菜
    public static final String WAITER_ORDER_DISH_URL = "waiter/order/dish";

    // 服务员确认订单
    public static final String WAITER_ORDER_DISH_CONFIRM_URL = "waiter/order/dish/confirm";

    public static final String WAITER_QUERY_CHECKOUT_URL = "waiter/query/checkout";

    // 服务员退菜
    public static final String WAITER_BACK_DISH_URL = "waiter/back/dish";

    // 服务员并台
    public static final String WAITER_TABLE_MERGE_URL = "waiter/table/merge";

    // 服务员查看所有呼叫服务
    public static final String WAITER_MESSAGE_LIST_URL = "waiter/message";


    /**********************吧台客户端*************************/
    // 吧台客户端餐台
    public static final String BAR_TABLE_URL = "bar/table";

    // 吧台客户端开台
    public static final String BAR_TABLE_OPEN_URL = "bar/table/open";

    // 吧台客户端换台
    public static final String BAR_TABLE_CHANGE_URL = "bar/table/change";

    // 吧台客户端清台
    public static final String BAR_TABLE_CLEAN_URL = "bar/table/clean";

    // 吧台客户端并台
    public static final String BAR_TABLE_MERGE_URL = "bar/table/merge";

    // 吧台登录
    public static final String BAR_LOGIN = "bar/login";

    // 吧台餐段
    public static final String BAR_MEAL_PERIOD_URL = "bar/meal/period";

    // 吧台催菜
    public static final String BAR_CALL_DISH = "bar/call/dish";

    // 吧台退菜
    public static final String BAR_BACK_DISH = "bar/back/dish";

    // 吧台结账单
    public static final String BAR_CHECKOUT_URL = "bar/checkout";

    // 吧台新增消费
    public static final String BAR_ORDER_DISH_ADD_URL = "bar/order/dish/add";

    // 吧台vip
    public static final String BAR_VIP_URL = "bar/vip";

    // 吧台对账
    public static final String BAR_CONTRAST_URL = "bar/contrast";

    // 吧台酒水标缺少
    public static final String BAR_WINE_MARK = "bar/wine/mark";

    /**********************微信*************************/
    public static final String WECHAT_URL = "wechat";


    /**********************后厨管理端*************************/
    public static final String COOK_URL = "cook";

    public static final String COOK_ORDER_MANAGEMENT_URL = "cook/ordermanage";

    /**********************注册机*************************/
    public static final String REGISTER_URL = "register";

}

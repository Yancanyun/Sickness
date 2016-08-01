package com.emenu.common.enums.other;

/**
 * 常量表枚举
 * @author gaoyang
 * @date 2013-08-08
 */
public enum ConstantEnum {

    SaleRank("SaleRank","销量排行"),//销量排行
    WebDomain("WebDomain", "网站域名"),   // 网站的域名，用于二维码生成
    InternalNetworkAddress("INTERNAL_NETWORK_ADDRESS", "内网地址"),   // 内网地址，用来禁止外网进行访问
    CookPrinterIp("CookPrinterIp", "后厨打印机ip"),
    BarPrinterIp("BarPrinterIp", "吧台打印机ip"),
    BackDishPrinterIp("BackDishPrinterIp", "退菜打印机ip"),
	
	 /*************************外卖相关参数*********************************/
    RestaurantName("restaurantName","餐馆名称"),
    RestaurantAddress("restaurantAddress","餐馆住址"),
    RestaurantPhone("restaurantPhone","餐馆电话"),
    TakeoutMinCost("takeoutMinCost","起送价格"),
    TakeoutStartTime("takeoutStartTime","外卖开始时间"),
    TakeoutEndTime("takeoutEndTime","外卖结束时间"),
    /*************************外卖相关参数*********************************/
	ImgPath("imgPath","图片路径"),


	/*************************会员多倍积分相关参数**************************/
    VipIntegralRuleCashToIntegral("VIP_INTEGRAL_RULE_CASH_TO_INTEGRAL","现金兑换积分"),
    VipIntegralRuleCardToIntegral("VIP_INTEGRAL_RULE_CARD_TO_INTEGRAL","刷卡兑换积分"),
    VipIntegralRuleOnlineToIntegral("VIP_INTEGRAL_RULE_ONLINE_TO_INTEGRAL","在线支付兑换积分"),
    VipIntegralRuleIntegralToMoney("VIP_INTEGRAL_RULE_INTEGRAL_TO_MONEY","积分兑换现金"),
    VipIntegralRuleCompleteInfoIntegral("VIP_INTEGRAL_RULE_COMPLETE_INFO_INTEGRAL","完善信息赠送积分"),
    VipIntegralRuleMeansOfStartIntegral("VIP_INTEGRAL_RULE_MEANS_OF_START_INTEGRAL","启动多倍积分方式"),

    /**************************会员等级相关参数*****************************/
    VipGradeVipAutoUpgradeState("VIP_GRADE_VIP_AUTO_UPGRADE_STATE","是否自动升级会员等级"),

    DishCategoryLayers("DISH_CATEGORY_LAYERS", "菜品分类等级数"),

    /**************************会员等级相关参数*****************************/


    /**************************智能排菜相关参数*****************************/

    AutoPrintOrderDishStartStatus("AUTO_PRINT_ORDER_DISH_START_STATUS","智能排菜启动状态"),
    PrinterPrintMaxNum("PRINTER_PRINT_MAX_NUM","菜品打印机最多可打印的菜品数量,相同菜品若能同时在一锅中做出只算一份"),
    ;


    private String key;
    private String description;

    ConstantEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }
}

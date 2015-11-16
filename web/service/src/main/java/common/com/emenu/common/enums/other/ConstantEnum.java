package com.emenu.common.enums.other;

/**
 * 常量表枚举
 * @author gaoyang
 * @date 2013-08-08
 */
public enum ConstantEnum {

    SaleRank("SaleRank","销量排行"),//销量排行
    WebDomain("WebDomain", "网站域名"),   // 网站的域名，用于二维码生成
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
	Integral_Rule_Complete_Info("INTEGR_RULE_COMPLETE_INFO", "完善会员信息所得积分"),
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

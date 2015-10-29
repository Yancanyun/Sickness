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

    // 后台登录url
    public static final String ADMIN_LOGIN_URL = "admin/login";

    // 后台权限管理
    public static final String ADMIN_PARTY_SECURITY_PERMISSION = "admin/party/security/permission";

    // 后台安全组管理
    public static final String ADMIN_PARTY_SECURITY_GROUP = "admin/party/security/group";

    //搜索风向标url
    public static final String KEYWORDS_URL = "admin/keywords";

    //餐台区域管理页面
    public static final String AREA_URL = "admin/restaurant/area";

    //餐台管理页面
    public static final String TABLE_URL = "admin/restaurant/table";

    //餐台二维码管理页面
    public static final String QRCODE_URL = "admin/restaurant/qrcode";

    //点餐平台首页图片管理url
    public static final String INDEX_IMG_URL = "admin/index/img";

    //会员基本信息管理
    public static final String VIP_VIPINFO_URL = "admin/party/group/vip";

    public static final String EMPLOYEE_MANAGEMENT = "admin/party/employee";

}

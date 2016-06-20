package com.emenu.common.enums.wechat;

/**
 * ConstantKeyEnums
 * 常量的key枚举
 *
 * @author: zhangteng
 * @time: 2014/7/12 14:57
 */
public enum ConstantKeyEnums {

    SensitiveCodeKey("SENSITIVE_CODE"),//敏感密码
    WebStatisticsCode("WEB_STATISTICS_CODE"),
    WeChatSystemErrorMsg("WECHAT_SYSTEM_ERROR_MSG"), // 微信端系统错误时回复的消息
    WeChatMenuNullMsg("WECHAT_MENU_NULL_MSG"),// 微信端菜单不存在时回复的消息
    WeChatMenuBindMsg("WECHAT_MENU_BIND_MSG"),// 微信端绑定PIN时回复的消息
    WeChatMenuUnbindMsg("WEHCAT_MENU_UNBIND_MSG"),// 微信端解除绑定是回复的消息
    WeChatMenuViewMaintenanceOrderMsg("WECHAT_MENU_VIEW_MAINTENANCE_ORDER_MSG"),// 微信端查看未完成订单时回复的消息
    WeChatNotBindMsg("WECHAT_NOT_BIND_MSG"),// 微信端帐号未绑定时回复的消息
    WeChatWelcomeTitle("WECHAT_WELCOME_TITLE"),// 微信欢迎图文信息的标题
    WeChatWelcomeDesc("WECHAT_WELCOME_DESC"),// 微信欢迎图文信息的描述
    WeChatWelcomePicUrl("WECHAT_WELCOME_PIC_URL"),// 微信欢迎图文信息的图片地址
    WeChatWelcomeUrl("WECHAT_WELCOME_URL") // 微信欢迎图文信息“查看全文”url
    ;//全站统计代码

    ConstantKeyEnums(String name) {
        this.name = name;
    }

    ;

    private String name;

    public String getName() {
        return name;
    }
}

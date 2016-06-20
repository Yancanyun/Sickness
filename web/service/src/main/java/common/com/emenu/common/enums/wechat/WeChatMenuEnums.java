package com.emenu.common.enums.wechat;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信菜单(Click型)枚举类
 *
 * @author: yangch
 * @time: 2016/6/20 14:14
 */
public enum WechatMenuEnums {

    MenuNull("null", "M_NULL"),
    QueryPoint("查询积分","QUERY_POINT"),
    ;

    private String name;

    private String key;

    WechatMenuEnums(String name, String key) {
        this.name = name;
        this.key = key;
    }

    private static Map<String, WechatMenuEnums> map = new HashMap<String, WechatMenuEnums>();

    // 回复消息map
    private static Map<String, ConstantKeyEnums> respMsgMap = new HashMap<String, ConstantKeyEnums>();

    static {
        for (WechatMenuEnums enums : WechatMenuEnums.values()) {
            map.put(enums.getKey(), enums);
        }
        initRespMsgMap();
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public ConstantKeyEnums getRespMsg() {
        return respMsgMap.get(key);
    }

    public static WechatMenuEnums valueOfByKey(String key) {
        return valueOfByKey(key, MenuNull);
    }

    public static WechatMenuEnums valueOfByKey(String key, WechatMenuEnums defaultValue) {
        WechatMenuEnums weChatMenuEnums = map.get(key);

        return weChatMenuEnums == null ? defaultValue : weChatMenuEnums;
    }

    private static void initRespMsgMap() {
        // 菜单不存在时返回的消息
        respMsgMap.put(MenuNull.getKey(),
                ConstantKeyEnums.WeChatMenuNullMsg);
    }
}

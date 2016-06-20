package com.emenu.common.enums.wechat;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信菜单(Click型)枚举类
 *
 * @author: yangch
 * @time: 2016/6/20 14:14
 */
public enum WeChatMenuEnums {
    MenuNull("null", "M_NULL"),
    Bond("绑定/解绑","BOND"),
    QueryPoint("查询积分","QUERY_POINT"),
    QueryBalance("查询余额","QUERY_BALANCE"),
    ;

    private String name;

    private String key;

    WeChatMenuEnums(String name, String key) {
        this.name = name;
        this.key = key;
    }

    private static Map<String, WeChatMenuEnums> map = new HashMap<String, WeChatMenuEnums>();

    // 回复消息map
    private static Map<String, ConstantKeyEnums> respMsgMap = new HashMap<String, ConstantKeyEnums>();

    static {
        for (WeChatMenuEnums enums : WeChatMenuEnums.values()) {
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

    public static WeChatMenuEnums valueOfByKey(String key) {
        return valueOfByKey(key, MenuNull);
    }

    public static WeChatMenuEnums valueOfByKey(String key, WeChatMenuEnums defaultValue) {
        WeChatMenuEnums weChatMenuEnums = map.get(key);

        return weChatMenuEnums == null ? defaultValue : weChatMenuEnums;
    }

    private static void initRespMsgMap() {
        // 菜单不存在时返回的消息
        respMsgMap.put(MenuNull.getKey(),
                ConstantKeyEnums.WeChatMenuNullMsg);
    }
}
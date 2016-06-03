package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 上菜方式枚举
 *
 * @author: yangch
 * @time: 2015/11/2 15:22
 */
public enum LoginTypeEnums {
    WechatBook(1, "微信下单"),
    Otherbook(2, "其他方式下单");

    private Integer id;
    private String bookType;

    LoginTypeEnums(Integer id, String bookType) {
        this.id = id;
        this.bookType = bookType;
    }

    private static Map<Integer, LoginTypeEnums> map = new HashMap<Integer, LoginTypeEnums>();

    static {
        for (LoginTypeEnums enums : LoginTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static LoginTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static LoginTypeEnums valueOf(int id, LoginTypeEnums defaultValue) {
        LoginTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getBookType() {
        return bookType;
    }
}

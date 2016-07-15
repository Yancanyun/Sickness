package com.emenu.common.enums.checkout;

import java.util.HashMap;
import java.util.Map;

/**
 * 结账单状态枚举
 *
 * @author: quanyibo
 * @time: 2016/7/15
 */
public enum CheckOutStatusEnums {

    IsNotCheckOut(0, "未结账"),
    IsCheckOut(1, "已结账"),
    ;

    private Integer id;
    private String status;

    CheckOutStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, CheckOutStatusEnums> map = new HashMap<Integer, CheckOutStatusEnums>();

    static {
        for (CheckOutStatusEnums enums : CheckOutStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static CheckOutStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static CheckOutStatusEnums valueOf(int id, CheckOutStatusEnums defaultValue) {
        CheckOutStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}

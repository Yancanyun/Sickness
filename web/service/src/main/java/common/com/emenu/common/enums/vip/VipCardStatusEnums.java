package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * VipCardStatusEnums
 *
 * @author: yangch
 * @time: 2015/11/2 15:22
 */
public enum VipCardStatusEnums {
    Disabled(0, "停用"),
    Enabled(1, "可用"),
    Deleted(2, "已删除");

    private Integer id;
    private String status;

    VipCardStatusEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, VipCardStatusEnums> map = new HashMap<Integer, VipCardStatusEnums>();

    static {
        for (VipCardStatusEnums enums : VipCardStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static VipCardStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static VipCardStatusEnums valueOf(int id, VipCardStatusEnums defaultValue) {
        VipCardStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}

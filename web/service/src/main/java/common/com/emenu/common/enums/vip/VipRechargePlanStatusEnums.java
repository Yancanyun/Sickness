package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * VipRechargePlanStatusEnums
 *
 * @author: yangch
 * @time: 2015/12/14 15:07
 */
public enum  VipRechargePlanStatusEnums {
    Disabled(0, "停用"),
    Enabled(1, "可用"),
    Deleted(2, "已删除");

    private Integer id;
    private String status;

    VipRechargePlanStatusEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, VipRechargePlanStatusEnums> map = new HashMap<Integer, VipRechargePlanStatusEnums>();

    static {
        for (VipRechargePlanStatusEnums enums : VipRechargePlanStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static VipRechargePlanStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static VipRechargePlanStatusEnums valueOf(int id, VipRechargePlanStatusEnums defaultValue) {
        VipRechargePlanStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }

}

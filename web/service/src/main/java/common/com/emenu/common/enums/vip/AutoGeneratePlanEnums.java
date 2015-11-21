package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyuting
 * @date 2015/11/19 17:19
 */
public enum AutoGeneratePlanEnums {

    GenerateAllByDiscount(1, "根据折扣生成全部"),
    GenerateAllByDifference(2, "根据差价生成全部"),
    GenerateByDiscount(3, "根据折扣生成部分"),
    GenerateByDifference(4, "根据差价生成部分");

    private Integer id;
    private String status;

    AutoGeneratePlanEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, AutoGeneratePlanEnums> map = new HashMap<Integer, AutoGeneratePlanEnums>();

    static {
        for (AutoGeneratePlanEnums autoGeneratePlanEnums : AutoGeneratePlanEnums.values()) {
            map.put(autoGeneratePlanEnums.getId(), autoGeneratePlanEnums);
        }
    }

    public static AutoGeneratePlanEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static AutoGeneratePlanEnums valueOf(int id, AutoGeneratePlanEnums defaultValue) {
        AutoGeneratePlanEnums autoGeneratePlanEnums = map.get(id);
        return autoGeneratePlanEnums == null ? defaultValue : autoGeneratePlanEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}

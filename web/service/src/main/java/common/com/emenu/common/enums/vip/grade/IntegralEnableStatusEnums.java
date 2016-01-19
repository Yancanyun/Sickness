package com.emenu.common.enums.vip.grade;

import java.util.HashMap;
import java.util.Map;

/**
 * MultipleIntegralPlanStatusEnums
 *
 * @author WangLM
 * @date 2015/12/7 14:12
 */
public enum IntegralEnableStatusEnums {
    Disabled(0, "停用"),
    Enabled(1, "启用");

    private Integer id;
    private String status;

    IntegralEnableStatusEnums(int id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, IntegralEnableStatusEnums> map = new HashMap<Integer, IntegralEnableStatusEnums>();

    static {
        for (IntegralEnableStatusEnums enums : IntegralEnableStatusEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static IntegralEnableStatusEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static IntegralEnableStatusEnums valueOf(int id, IntegralEnableStatusEnums defaultValue){
        IntegralEnableStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}

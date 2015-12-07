package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * MultipleIntegralPlanStatusEnums
 *
 * @author WangLM
 * @date 2015/12/7 14:12
 */
public enum  MultipleIntegralPlanStatusEnums {
    Disabled(0, "停用"),
    Enabled(1, "启用");

    private Integer id;
    private String status;

    MultipleIntegralPlanStatusEnums(int id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, MultipleIntegralPlanStatusEnums> map = new HashMap<Integer, MultipleIntegralPlanStatusEnums>();

    static {
        for (MultipleIntegralPlanStatusEnums enums : MultipleIntegralPlanStatusEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static MultipleIntegralPlanStatusEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static MultipleIntegralPlanStatusEnums valueOf(int id, MultipleIntegralPlanStatusEnums defaultValue){
        MultipleIntegralPlanStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}

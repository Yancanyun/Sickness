package com.emenu.common.enums.meal;

import java.util.HashMap;
import java.util.Map;

/**
 * MealPeriodStatusEnums
 *
 * @author Wang Liming
 * @date 2015/11/11 10:20
 */
public enum MealPeriodStatusEnums {
    Disabled(0, "停用"),
    Enabled(1, "启用");

    private Integer id;
    private String status;

    MealPeriodStatusEnums(int id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, MealPeriodStatusEnums> map = new HashMap<Integer, MealPeriodStatusEnums>();

    static {
        for (MealPeriodStatusEnums enums : MealPeriodStatusEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static MealPeriodStatusEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static MealPeriodStatusEnums valueOf(int id, MealPeriodStatusEnums defaultValue){
        MealPeriodStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}

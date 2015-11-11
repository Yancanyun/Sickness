package com.emenu.common.enums.meal;

import java.util.HashMap;
import java.util.Map;

/**
 * MealPeriodStateEnums
 *
 * @author Wang Liming
 * @date 2015/11/11 10:20
 */
public enum MealPeriodStateEnums {
    Disabled(0, "停用"),
    Enabled(1, "启用");

    private Integer id;
    private String state;

    MealPeriodStateEnums(int id, String state) {
        this.id = id;
        this.state = state;
    }

    private static Map<Integer, MealPeriodStateEnums> map = new HashMap<Integer, MealPeriodStateEnums>();

    static {
        for (MealPeriodStateEnums enums : MealPeriodStateEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static MealPeriodStateEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static MealPeriodStateEnums valueOf(int id, MealPeriodStateEnums defaultValue){
        MealPeriodStateEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}

package com.emenu.common.enums.meal;

import java.util.HashMap;
import java.util.Map;

/**
 * MealPeriodStateEnums
 *
 * @author Wang Liming
 * @date 2015/11/10 14:21
 */
public enum MealPeriodStateEnums {

    Using(1,"正在使用"),
    UnUsing(0,"未使用");

    private Integer id;
    private String description;


    MealPeriodStateEnums(Integer id, String description){
        this.id = id;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}

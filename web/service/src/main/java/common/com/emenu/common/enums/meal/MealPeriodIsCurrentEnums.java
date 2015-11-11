package com.emenu.common.enums.meal;

import java.util.HashMap;
import java.util.Map;

/**
 * MealPeriodIsCurrentEnums
 *
 * @author Wang Liming
 * @date 2015/11/10 14:21
 */
public enum MealPeriodIsCurrentEnums {

    Using(1,"正在使用"),
    UnUsing(0,"未使用");

    private Integer id;
    private String description;


    MealPeriodIsCurrentEnums(Integer id, String description){
        this.id = id;
        this.description = description;
    }

    private static Map<Integer, MealPeriodIsCurrentEnums> map = new HashMap<Integer, MealPeriodIsCurrentEnums>();

    static {
        for (MealPeriodIsCurrentEnums enums : MealPeriodIsCurrentEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static MealPeriodIsCurrentEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static MealPeriodIsCurrentEnums valueOf(int id, MealPeriodIsCurrentEnums defaultValue){
        MealPeriodIsCurrentEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

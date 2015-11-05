package com.emenu.common.enums.dish;

import java.util.HashMap;
import java.util.Map;

/**
 * UnitEnum
 * 单位类型的枚举
 * @author dujuan
 * @date 2015/11/3
 */
public enum  UnitEnum {
    HUNDREDWEIGHT(1 , "重量单位"),
    QUANTITY(2 , "数量单位")
    ;
    private Integer id;
    private String name;

    UnitEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private static Map<Integer, UnitEnum> map = new HashMap<Integer, UnitEnum>();

    static {
        for(UnitEnum unitEnum : UnitEnum.values()) {
            map.put(unitEnum.getId(), unitEnum);
        }
    }

    public static UnitEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static UnitEnum valueOf(int id, UnitEnum defaultValue) {
        UnitEnum unitEnum = map.get(id);
        return unitEnum == null ? defaultValue : unitEnum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

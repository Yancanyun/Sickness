package com.emenu.common.enums.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * StockOutTypeEnums
 *
 * @author: zhangteng
 * @time: 2015/11/17 20:05
 **/
public enum IngredientStatusEnums {

    Normal(1, "正常使用"),
    Delete(2, "已删除"),
    CanUpdate(0,"可编辑"),
    NotUpdate(1,"不可编辑"),
    ;

    private Integer id;

    private String type;

    IngredientStatusEnums(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, IngredientStatusEnums> map = new HashMap<Integer, IngredientStatusEnums>();

    static {
        for (IngredientStatusEnums enums : IngredientStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static IngredientStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static IngredientStatusEnums valueOf(int id, IngredientStatusEnums defaultValue) {
        IngredientStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
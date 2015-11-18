package com.emenu.common.enums.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * StockOutTypeEnums
 *
 * @author: zhangteng
 * @time: 2015/11/17 20:05
 **/
public enum StockOutTypeEnums {

    Average(1, "加权平均"),
    Manual(2, "手动"),
    ;

    private Integer id;

    private String type;

    StockOutTypeEnums(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, StockOutTypeEnums> map = new HashMap<Integer, StockOutTypeEnums>();

    static {
        for (StockOutTypeEnums enums : StockOutTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static StockOutTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static StockOutTypeEnums valueOf(int id, StockOutTypeEnums defaultValue) {
        StockOutTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
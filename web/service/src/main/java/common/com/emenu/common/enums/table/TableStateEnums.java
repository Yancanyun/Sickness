package com.emenu.common.enums.table;

import java.util.HashMap;
import java.util.Map;

/**
 * TableEnumss
 *
 * @author: yangch
 * @time: 2015/10/23 18:49
 */
public enum TableStateEnums {
    Disabled(0, "停用"),
    Enabled(1, "可用"),
    Checkouted(2, "占用已结账"),
    Unchekouted(3, "占用未结账"),
    Booked(4, "已预订"),
    Merged(5, "已并桌"),
    Deleted(6, "已删除");

    private Integer id;
    private String state;

    TableStateEnums(Integer id, String type) {
        this.id = id;
        this.state = type;
    }

    private static Map<Integer, TableStateEnums> map = new HashMap<Integer, TableStateEnums>();

    static {
        for (TableStateEnums enums : TableStateEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static TableStateEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static TableStateEnums valueOf(int id, TableStateEnums defaultValue) {
        TableStateEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return state;
    }
}

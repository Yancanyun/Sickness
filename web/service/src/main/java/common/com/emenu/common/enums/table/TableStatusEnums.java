package com.emenu.common.enums.table;

import java.util.HashMap;
import java.util.Map;

/**
 * TableStatusEnums
 *
 * @author: yangch
 * @time: 2015/10/23 18:49
 */
public enum TableStatusEnums {
    Disabled(0, "停用"),
    Enabled(1, "可用"),
    Checkouted(2, "占用已结账"),
    Uncheckouted(3, "占用未结账"),
    Merged(4, "已并桌"),
    Booked(5, "已预订"),
    Deleted(6, "已删除");

    private Integer id;
    private String status;

    TableStatusEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, TableStatusEnums> map = new HashMap<Integer, TableStatusEnums>();

    static {
        for (TableStatusEnums enums : TableStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static TableStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static TableStatusEnums valueOf(int id, TableStatusEnums defaultValue) {
        TableStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}

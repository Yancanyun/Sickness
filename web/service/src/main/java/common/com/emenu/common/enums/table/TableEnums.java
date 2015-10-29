package com.emenu.common.enums.table;

import java.util.HashMap;

/**
 * TableEnumss
 *
 * @author: yangch
 * @time: 2015/10/23 18:49
 */
public enum TableEnums {
    Disabled(0, "停用"),
    Enabled(1, "可用"),
    Checkouted(2, "占用已结账"),
    Unchekouted(3, "占用未结账"),
    Booked(4, "已预订"),
    Merged(5, "已并桌"),
    Deleted(6, "已删除");

    private Integer id;
    private String description;

    TableEnums(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static HashMap<Integer, String> tableEnumsMap;

    static {
        tableEnumsMap = new HashMap<Integer, String>();

        for(TableEnums tableEnums : TableEnums.values()) {
            tableEnumsMap.put(tableEnums.getId(), tableEnums.getDescription());
        }
    }

    public static String getDescriptionById(Integer id) {
        String desc = tableEnumsMap.get(id);
        if(desc!=null && !desc.equals("")) {
            return desc;
        } else {
            return "获取错误";
        }
    }
}

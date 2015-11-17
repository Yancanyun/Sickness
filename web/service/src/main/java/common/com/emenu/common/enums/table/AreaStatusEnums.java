package com.emenu.common.enums.table;

import java.util.HashMap;
import java.util.Map;

/**
 * AreaStatusEnums
 *
 * @author: yangch
 * @time: 2015/11/2 15:22
 */
public enum AreaStatusEnums {
    Enabled(1, "可用"),
    Deleted(2, "已删除");

    private Integer id;
    private String status;

    AreaStatusEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, AreaStatusEnums> map = new HashMap<Integer, AreaStatusEnums>();

    static {
        for (AreaStatusEnums enums : AreaStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static AreaStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static AreaStatusEnums valueOf(int id, AreaStatusEnums defaultValue) {
        AreaStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}

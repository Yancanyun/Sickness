package com.emenu.common.enums.table;

import java.util.HashMap;
import java.util.Map;

/**
 * AreaStateEnums
 *
 * @author: yangch
 * @time: 2015/11/2 15:22
 */
public enum AreaStateEnums {
    Enabled(1, "可用"),
    Deleted(2, "已删除");

    private Integer id;
    private String state;

    AreaStateEnums(Integer id, String type) {
        this.id = id;
        this.state = type;
    }

    private static Map<Integer, AreaStateEnums> map = new HashMap<Integer, AreaStateEnums>();

    static {
        for (AreaStateEnums enums : AreaStateEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static AreaStateEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static AreaStateEnums valueOf(int id, AreaStateEnums defaultValue) {
        AreaStateEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return state;
    }
}
